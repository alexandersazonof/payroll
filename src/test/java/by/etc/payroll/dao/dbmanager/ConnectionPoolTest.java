package by.etc.payroll.dao.dbmanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConnectionPoolTest {

    @Test
    public void connectionPoolInitTest() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }

    @Test
    public void connectionPoolGetInstanceTest() {
        ConnectionPool actual = ConnectionPool.getInstance();
        ConnectionPool expected = ConnectionPool.getInstance();

        assertEquals(actual,expected);
    }
}
