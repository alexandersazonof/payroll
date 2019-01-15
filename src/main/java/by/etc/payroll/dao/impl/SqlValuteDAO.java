package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.ExchangeRate;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.dao.ValuteDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.creator.Creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlValuteDAO implements ValuteDAO {

    private static final String SELECT_ALL_FROM_VALUTE = "select * from valute";
    private static final String SELECT_BY_NAME = "select * from valute where name = ?";
    private static final String SELECT_BY_ID = "select * from valute where id = ?";
    private static final String SELECT_FROM_EXACHANGE_RATE = "select * from exachange_rates";
    private static final String SELECT_FROM_EXACHANGE_RATE_BY_ID = "select * from exachange_rates where id = ?";

    private static final String UPDATE_EXCHANGE_RATE_BY_ID = "update exachange_rates set from_valute_id = ? , to_valute_id = ?,  course = ? where id = ?";

    private static final String FIELD_VALUTE_ID = "id";
    private static final String FIELD_VALUTE_NAME = "name";

    private static final String FIELD_EXC_ID = "id";
    private static final String FIELD_EXC_FROM_VALUTE_ID = "from_valute_id";
    private static final String FIELD_EXC_TO_VALUTE_ID = "to_valute_id";
    private static final String FIELD_EXC_COURSE = "course";


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
    public List<ExchangeRate> getAllExchangeRate() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ExchangeRate> exchangeRateList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_FROM_EXACHANGE_RATE);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(FIELD_EXC_ID);
                int fromValuteId = resultSet.getInt(FIELD_EXC_FROM_VALUTE_ID);
                int toValuteId = resultSet.getInt(FIELD_EXC_TO_VALUTE_ID);
                float course = resultSet.getFloat(FIELD_EXC_COURSE);

                ExchangeRate exchangeRate = Creator.takeExchangeRate(id, fromValuteId, toValuteId, course);
                exchangeRateList.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }

        return exchangeRateList;
    }

    @Override
    public ExchangeRate getExchangeRateById(int id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExchangeRate rate = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_FROM_EXACHANGE_RATE_BY_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int fromValuteId = resultSet.getInt(FIELD_EXC_FROM_VALUTE_ID);
                int toValuteId = resultSet.getInt(FIELD_EXC_TO_VALUTE_ID);
                float course = resultSet.getFloat(FIELD_EXC_COURSE);
                rate = Creator.takeExchangeRate(id, fromValuteId, toValuteId, course);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return rate;
    }

    @Override
    public boolean updateExchangeRateById(ExchangeRate exchangeRate) throws DAOException {
        PreparedStatement statement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(UPDATE_EXCHANGE_RATE_BY_ID);
            statement.setInt(1, exchangeRate.getFromValuteId());
            statement.setInt(2, exchangeRate.getToValuteId());
            statement.setFloat(3, exchangeRate.getCourse());
            statement.setInt(4, exchangeRate.getId());

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
    public boolean insert(Valute valute) throws DAOException {
        return false;
    }

    @Override
    public Valute find(int id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Valute valute = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                valute = Creator.takeValute(id, resultSet.getString(FIELD_VALUTE_NAME));
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
        return valute;
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
