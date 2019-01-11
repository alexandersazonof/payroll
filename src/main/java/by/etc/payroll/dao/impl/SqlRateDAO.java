package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Rate;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.dao.RateDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.creator.Creator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlRateDAO implements RateDAO {

    private static final String SELECT_ALL_FROM_RATE = "select * from rate";
    private static final String SELECT_BY_NAME = "select * from rate where name = ?";
    private static final String SELECT_BY_ID = "select * from rate where id = ?";

    private static final String FIELD_RATE_ID = "id";
    private static final String FIELD_VATE_NAME = "name";
    private static final String FIELD_RATE_DESCRIPTION = "description";


    @Override
    public List<Rate> getAll() throws DAOException{
        List<Rate> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_FROM_RATE);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(FIELD_RATE_ID);
                String name = resultSet.getString(FIELD_VATE_NAME);
                String description = resultSet.getString(FIELD_RATE_DESCRIPTION);

                list.add(Creator.takeRate(id, name, description));
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return list;
    }

    @Override
    public int getIdByName(String name) throws DAOException {
        int id =0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_NAME);
            statement.setString(1, name);


            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(FIELD_RATE_ID);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return id;
    }

    @Override
    public boolean insert(Rate rate) throws DAOException {
        return false;
    }

    @Override
    public Rate find(int id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Rate rate = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rate = Creator.takeRate(id, resultSet.getString(FIELD_VATE_NAME), resultSet.getString(FIELD_RATE_DESCRIPTION));
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return rate;
    }

    @Override
    public boolean update(Rate rate) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Rate rate) throws DAOException {
        return false;
    }
}
