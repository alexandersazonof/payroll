package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Operation;
import by.etc.payroll.dao.OperationDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlOperationDAO implements OperationDAO{
    private final String INSERT_INTO = "insert into operation (action, date, account_number, user_id) values (?, ?, ?, ?);";


    @Override
    public boolean insert(Operation operation) throws DAOException {
        PreparedStatement statement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(INSERT_INTO);
            statement.setString(1, operation.getAction());
            statement.setString(2, operation.getDate());
            statement.setString(3, operation.getNumber());
            statement.setInt(4, operation.getUserId());

            return statement.execute();

        } catch (SQLException e) {

            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                statement.close();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public Operation find(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Operation operation) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Operation operation) throws DAOException {
        return false;
    }
}
