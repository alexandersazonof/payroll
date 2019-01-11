package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Transaction;
import by.etc.payroll.dao.TransactionDAO;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlTransactionDAO implements TransactionDAO<Transaction> {
    private final String INSERT_TRANSACTION = "insert into transaction(From_Number,To_Number,Count,Date) values (?, ?, ?, ?);";
    private final String UPDATE_COUNT_BY_NUMBER = "update account set Count = Count - ? where Number = ?";
    private final String SELECT_TRANSACTION_BY_USER_ID = "select * from transaction where From_Number or To_Number in\n" +
            "                                (select Number from account where User_ID =? );";


    private Logger LOG = LogManager.getLogger(SqlTransactionDAO.class);
    private final String FORMAT = "yyyy-MM-dd";
    private final String TRANSACTION_ID = "ID";
    private final String TRANSACTION_FROM_NUMBER = "From_Number";
    private final String TRANSACTION_TO_NUMBER = "To_Number";
    private final String TRANSACTION_COUNT = "Count";
    private final String TRANSACTION_DATE = "Date";

    @Override
    public boolean insert(Transaction transaction) throws DAOException {
        return false;
    }

    @Override
    public Transaction find(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Transaction transaction) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Transaction transaction) throws DAOException {
        return false;
    }

    public boolean doTransacation (Transaction transaction) throws DAOException {
        PreparedStatement statement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            connection.setAutoCommit(false);
            try {
                 statement = connection.prepareStatement(INSERT_TRANSACTION);

                statement.setString(1, transaction.getFromNumber());
                statement.setString(2, transaction.getToNumber());
                statement.setInt(3, transaction.getCount());
                statement.setString(4, transaction.getDate());
                statement.executeUpdate();



                statement = connection.prepareStatement(UPDATE_COUNT_BY_NUMBER);

                statement.setInt(1, transaction.getCount());
                statement.setString(2, transaction.getFromNumber());
                statement.executeUpdate();


                statement = connection.prepareStatement(UPDATE_COUNT_BY_NUMBER);

                statement.setInt(1, transaction.getCount() * (-1));
                statement.setString(2, transaction.getToNumber());
                statement.executeUpdate();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                connection.rollback();
                throw new DAOException(e);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return false;
    }

    @Override
    public List<Transaction> getAllByUserId(int userId) throws DAOException{
        List<Transaction> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_TRANSACTION_BY_USER_ID);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int transactionId = resultSet.getInt(TRANSACTION_ID);
                String transactionFromNumber = resultSet.getString(TRANSACTION_FROM_NUMBER);
                String transactionToNumber = resultSet.getString(TRANSACTION_TO_NUMBER);
                int transactionCount = resultSet.getInt(TRANSACTION_COUNT);
                String transactionDate = resultSet.getString(TRANSACTION_DATE);

                list.add(create(transactionId, transactionFromNumber, transactionToNumber,
                            transactionCount, transactionDate));
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return list;
    }

    private Transaction create (int id, String fromAccount, String toAccount, int count, String date) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setCount(count);
        transaction.setFromNumber(fromAccount);
        transaction.setToNumber(toAccount);
        SimpleDateFormat dmyFormat = new SimpleDateFormat(FORMAT);
        transaction.setDate(date);
        return transaction;
    }
}
