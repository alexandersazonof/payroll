package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Transaction;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.dao.ValuteDAO;
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

public class SqlValuteDAO implements ValuteDAO {

    private static final String SELECT_ALL_FROM_VALUTE = "select * from valute";
    private static final String SELECT_BY_NAME = "select * from valute where name = ?";

    private static final String FIELD_VALUTE_ID = "id";
    private static final String FIELD_VALUTE_NAME = "name";


    @Override
    public int getIdByName(String name) throws DAOException {
        int id =0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_NAME);
            statement.setString(1,name);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(FIELD_VALUTE_ID);
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
    public boolean insert(Valute valute) throws DAOException {
        return false;
    }

    @Override
    public Valute find(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Valute valute) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Valute valute) throws DAOException {
        return false;
    }

    @Override
    public List<Valute> getAll() throws DAOException {
        List<Valute> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_FROM_VALUTE);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(FIELD_VALUTE_ID);
                String name = resultSet.getString(FIELD_VALUTE_NAME);
                list.add(Creator.takeValute(id, name));
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
}
