package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.UserData;
import by.etc.payroll.dao.UserDataDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUserDataDAO implements UserDataDAO {
    private final String INSERT_USER_DATA = "insert into user_data (first_name, last_name, address, city, id_card) values (?, ?, ?, ?, ?)";

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
}
