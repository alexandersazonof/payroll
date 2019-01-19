package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.UserData;
import by.etc.payroll.dao.UserDataDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.creator.Creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUserDataDAO implements UserDataDAO {
    private final String INSERT_USER_DATA = "insert into user_data (first_name, last_name, address, city, id_card) values (?, ?, ?, ?, ?)";
    private final String SELECT_USER_DATA_BY_LAST_NAME = "select * from user_data where last_name = ?";
    private final String DELETE_USER_DATA_BY_LAST_NAME = "delete from user_data where last_name = ?";

    private final String ID = "id";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME  = "last_name";
    private final String ADDRESS = "address";
    private final String CITY = "city";
    private final String ID_CARD = "id_card";

    @Override
    public boolean insert(UserData userData) throws DAOException {
        PreparedStatement statement = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(INSERT_USER_DATA);
            statement.setString(1, userData.getFirstName());
            statement.setString(2, userData.getLastName());
            statement.setString(3, userData.getAddress());
            statement.setString(4, userData.getCity());
            statement.setInt(5, userData.getIdCard());
            statement.execute();


        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
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
    public UserData find(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(UserData userData) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(UserData userData) throws DAOException {
        return false;
    }

    @Override
    public UserData findByLastName(String lastName) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserData userData = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_USER_DATA_BY_LAST_NAME);
            statement.setString(1, lastName);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String firstName = resultSet.getString(FIRST_NAME);
                String address = resultSet.getString(ADDRESS);
                String city = resultSet.getString(CITY);
                int idCard = resultSet.getInt(ID_CARD);

                userData = Creator.takeUserData(firstName, lastName, address, city, idCard);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return userData;
    }

    @Override
    public boolean deleteByLastName(String lastName) throws DAOException {
        PreparedStatement statement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            statement = connection.prepareStatement(DELETE_USER_DATA_BY_LAST_NAME);
            statement.setString(1, lastName);

            return statement.execute();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
