package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.Company;
import by.etc.payroll.bean.Rate;
import by.etc.payroll.dao.CompanyDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.creator.Creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlCompanyDAO implements CompanyDAO {
    private static final String SELECT_ALL_FROM_COMPANY = "select * from company";
    private static final String SELECT_BY_NAME = "select * from company where name = ?";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";

    @Override
    public List<Company> getAll() throws DAOException {
        List<Company> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_FROM_COMPANY);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(FIELD_ID);
                String name = resultSet.getString(FIELD_NAME);

                list.add(Creator.takeCompany(id, name));
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
                return resultSet.getInt(FIELD_ID);
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
    public boolean insert(Company company) throws DAOException {
        return false;
    }

    @Override
    public Company find(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Company company) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Company company) throws DAOException {
        return false;
    }
}
