package by.etc.payroll.dao.dbmanager;

import by.etc.payroll.dao.util.DatabaseConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger LOG = LogManager.getLogger(ConnectionPool.class);

    private static AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static AtomicBoolean isPoolClosed = new AtomicBoolean(false);
    private static Lock connectionPoolLock = new ReentrantLock(true);
    private static Lock closePoolLock = new ReentrantLock(true);
    private static int connectionAmount = 0;

    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> usedConnections;

    private static ConnectionPool poolInstance;

    private ConnectionPool() {

        usedConnections = new ArrayBlockingQueue<>(DatabaseConstants.POOL_SIZE);
        availableConnections = new ArrayBlockingQueue<>(DatabaseConstants.POOL_SIZE);
        try {
            Class.forName(DatabaseConstants.DRIVER).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            LOG.fatal("Error. Impossible to load driver.", e);
            throw new RuntimeException(e);
        }

        for (int i = 0; i < DatabaseConstants.POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(DatabaseConstants.URL, DatabaseConstants.LOGIN, DatabaseConstants.PASSWORD);
                availableConnections.put(new ProxyConnection(connection));
                connectionAmount++;
            } catch (SQLException | InterruptedException e) {
                LOG.error("Error. Impossible to get connection : " + i, e);
            }
        }
        if (connectionAmount < 1) {
            LOG.fatal("Error. Impossible to initialize connections. Connection amount < 1.");
            throw new RuntimeException("Error. Impossible to initialize connections");
        }
        LOG.info("Connections amount : " + connectionAmount);
    }

    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            connectionPoolLock.lock();
            try {
                if (poolInstance == null) {
                    poolInstance = new ConnectionPool();
                    isPoolCreated.set(true);
                }
            } finally {
                connectionPoolLock.unlock();
            }
        }
        return poolInstance;
    }


    public Connection getConnection() {
        ProxyConnection connection = null;
        if (!isPoolClosed.get()) {
            closePoolLock.lock();
            try {
                connection = availableConnections.take();
                usedConnections.put(connection);
            } catch (InterruptedException e) {
                LOG.error("Error. Impossible to get connection.", e);
            } finally {
                closePoolLock.unlock();
            }
        }
        return connection;
    }


    void releaseConnection(ProxyConnection connection) {
        if (!isPoolClosed.get()) {
            usedConnections.remove(connection);
            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                LOG.error("Error. Impossible to release connection.", e);
            }
        }
    }

    public void closePool() {
        if (!isPoolClosed.get()) {
            closePoolLock.lock();
            try {
                if (!isPoolClosed.get()) {
                    isPoolClosed.set(true);
                    for (int i = 0; i < connectionAmount; ++i) {
                        availableConnections.take().realClose();
                        LOG.info("Connection closed : " + i);
                    }
                }
            } catch (SQLException | InterruptedException e) {
                LOG.error("Error. Impossible to close connection pool.", e);
            } finally {
                closePoolLock.unlock();
            }
        }
    }

}
