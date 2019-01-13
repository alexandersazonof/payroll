package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Application;
import by.etc.payroll.dao.ApplicationDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlApplicationDAO implements ApplicationDAO {
    private final String INSERT_INTO = "insert into application (action, account_id) values(?, ?)";

    @Override
    public boolean insert(Application application) throws DAOException {
        PreparedStatement statement = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            statement = connection.prepareStatement(INSERT_INTO);
            statement.setString(1, application.getAction());
            statement.setInt(2, application.getAccountId());
            statement.execute();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public Application find(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Application application) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Application application) throws DAOException {
        return false;
    }
}
