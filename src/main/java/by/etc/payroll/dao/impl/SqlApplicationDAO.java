package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Application;
import by.etc.payroll.dao.ApplicationDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.creator.Creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlApplicationDAO implements ApplicationDAO {
    private final String INSERT_INTO = "insert into application (action, account_id) values(?, ?)";
    private final String DELETE_FROM_APPLICATION_BY_ACCOUNT_ID = "delete from application where account_id = ?";
    private final String SELECT_ALL = "select * from application";

    private final String FIELD_ID = "id";
    private final String FIELD_ACTION = "action";
    private final String FIELD_ACCOUNT_ID = "account_id";
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

    @Override
    public boolean deleteByNumberId(int id) throws DAOException {
        PreparedStatement statement = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(DELETE_FROM_APPLICATION_BY_ACCOUNT_ID);
            statement.setInt(1, id);

            return statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<Application> getAll() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Application> applicationList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_ALL);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(FIELD_ID);
                String action = resultSet.getString(FIELD_ACTION);
                int accountId = resultSet.getInt(FIELD_ACCOUNT_ID);

                Application application = Creator.takeApplication(action, accountId);
                applicationList.add(application);
            }


        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }

        return applicationList;
    }
}
