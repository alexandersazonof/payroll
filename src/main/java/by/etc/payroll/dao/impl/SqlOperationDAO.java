package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Operation;
import by.etc.payroll.dao.OperationDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.creator.Creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlOperationDAO implements OperationDAO{
    private final String INSERT_INTO = "insert into operation (action, date, account_number, user_id) values (?, ?, ?, ?);";
    private final String SELECT_BY_ACCOUNT_NUMBER = "select * from operation where account_number = ?";


    private final String OPERATION_ID = "id";
    private final String OPERATION_ACTION = "action";
    private final String OPERATION_DATE = "date";
    private final String OPERATION_NUMBER = "account_number";
    private final String OPERATIN_USER_ID = "user_id";

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

    @Override
    public List<Operation> getAllByNumber(String number) throws DAOException {
        List<Operation> operationList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_BY_ACCOUNT_NUMBER);
            statement.setString(1, number);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(OPERATION_ID);
                String action = resultSet.getString(OPERATION_ACTION);
                String date = resultSet.getString(OPERATION_DATE);
                int userId = resultSet.getInt(OPERATIN_USER_ID);

                Operation operation = Creator.takeOperation(id, action, number, date, userId);
                operationList.add(operation);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return operationList;
    }
}
