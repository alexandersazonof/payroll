package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.User;
import by.etc.payroll.dao.UserDAO;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.service.creator.Creator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlUserDAO implements UserDAO<User> {

    private final String SELECT_ALL_USER = "select * from user where role = 'Guest'";
    private final String SELECT_BY_LOGIN = "select * from user where Login = ?";
    private final String INSERT_USER = "insert into user (Login,password,email,LastName,FirstName,Role) values(?,?,?,?,?,?);";
    private final String SELECT_BY_EMAIL = "select * from user where email = ?";
    private final String SELECT_BY_EMAIL_NOT_CONSIDERING = "select * from user where email = ? and ID != ?";
    private final String SELECT_BY_LOGIN_NOT_CONSIDERING = "select * from user where Login = ? and ID != ?";
    private final String SELECT_BY_ID = "select * from user where ID = ?";
    private final String UPDATE_USER = "update user set Login = ? , Password = ? , LastName = ? , FirstName = ? , email = ? where ID = ?";


    private final String FIELD_USER_ID = "ID";
    private final String FIELD_USER_LOGIN = "Login";
    private final String FIELD_USER_PASSWORD = "Password";
    private final String FIELD_USER_LAST_NAME  = "LastName";
    private final String FIELD_USER_FIRST_NAME = "FirstName";
    private final String FIELD_USER_ROLE = "Role";
    private final String FIELD_USER_EMAIL = "email";

    private Logger LOG = LogManager.getLogger(SqlUserDAO.class);

    @Override
    public List<User> getAllUserWithoutPassword() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_ALL_USER);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(FIELD_USER_ID);
                String login = resultSet.getString(FIELD_USER_LOGIN);
                String lastName = resultSet.getString(FIELD_USER_LAST_NAME);
                String firstName = resultSet.getString(FIELD_USER_FIRST_NAME);
                String role = resultSet.getString(FIELD_USER_ROLE);
                String email = resultSet.getString(FIELD_USER_EMAIL);

                User user = Creator.takeUserWithoutPassword(id, login, lastName, firstName, role, email);

                userList.add(user);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return userList;
    }

    @Override
    public User findByLogin(String login) throws DAOException{
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

             statement = connection.prepareStatement(SELECT_BY_LOGIN);

             statement.setString(1,login);


            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = create(resultSet);
            }
        } catch (SQLException e) {

            LOG.error(e.getMessage(),e);
            throw new DAOException(e);

        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }

        return user;
    }

    @Override
    public User findByEmail(String email) throws DAOException {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_EMAIL);

            statement.setString(1,email);


            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = create(resultSet);
            }
        } catch (SQLException e) {

            LOG.error(e.getMessage(),e);
            throw new DAOException(e);

        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }

        return user;
    }

    @Override
    public User findByLoginNotConsideringId(String login, int id) throws DAOException {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_LOGIN_NOT_CONSIDERING);

            statement.setString(1,login);
            statement.setInt(2,id);


            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = create(resultSet);
            }
        } catch (SQLException e) {

            LOG.error(e.getMessage(),e);
            throw new DAOException(e);

        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }

        return user;
    }

    @Override
    public User findByEmailNotConsideringId(String email, int id) throws DAOException {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_EMAIL_NOT_CONSIDERING);

            statement.setString(1, email);
            statement.setInt(2, id);


            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = create(resultSet);
            }
        } catch (SQLException e) {

            LOG.error(e.getMessage(),e);
            throw new DAOException(e);

        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }

        return user;
    }

    @Override
    public boolean insert(User user) throws DAOException {
        PreparedStatement statement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getRole());

            return statement.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            try {

                statement.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }
    }

    @Override
    public User find(int id) throws DAOException {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_ID);

            statement.setInt(1, id);


            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = create(resultSet);
            }
        } catch (SQLException e) {

            LOG.error(e.getMessage(),e);
            throw new DAOException(e);

        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }

        return user;
    }

    @Override
    public boolean update(User user) throws DAOException {
        PreparedStatement statement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getId());

            return statement.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            try {

                statement.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }
    }

    @Override
    public boolean delete(User user) throws DAOException {
        return false;
    }

    private User create (ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("ID"),
                resultSet.getString("Login"),
                resultSet.getString("Password"),
                resultSet.getString("LastName"),
                resultSet.getString("FirstName"),
                resultSet.getString("Role"),
                resultSet.getString("email")
        );
    }
}
