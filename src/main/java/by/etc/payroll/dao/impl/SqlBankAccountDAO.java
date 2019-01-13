package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Company;
import by.etc.payroll.bean.Operation;
import by.etc.payroll.dao.BankAccountDAO;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.service.creator.Creator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlBankAccountDAO implements BankAccountDAO<BankAccount> {
    private Logger LOG = LogManager.getLogger(SqlBankAccountDAO.class);

    private final String SELECT_BY_USER_ID = "select * from account where User_ID = ?";
    private final String SELECT_BY_NUMBER = "select * from account where Number = ?";
    private final String UPDATE_BY_ID = "update account set Name = ? , Status = ? , Number = ? , Count = ?   where ID = ?";
    private final String INSERT_INTO = "insert into account (Name,Number,Status,Count,User_ID, Valute_ID) values (?, ?, ?, ?, ?, ?);";
    private final String DELETE_BY_NUMBER = "delete from account where Number = ?";
    private final String SELECT_BY_ID = "select * from account where id = ?";
    private final String SELECT_COURSE_FROM_EXACHANGE_RATE = "select course from exachange_rates where from_valute_id = ? and to_valute_id = ?";



    private final String UPDATE_ACCOUNT_A_COUNT_BY_NUMBER = "update account set Count = Count - ? where Number = ?";
    private final String UPDATE_CARD_A_MONEY_BY_ID = "update cards set money = money + ? where id = ?";


    private final String ACTION_SEND_MONEY = "Send money between their cards";

    private final String ACCOUNT_ID = "ID";
    private final String ACCOUNT_NAME = "Name";
    private final String ACCOUNT_NUMBER = "Number";
    private final String ACCOUNT_STATUS = "Status";
    private final String ACCOUNT_COUNT = "Count";
    private final String ACCOUNT_USER_ID = "User_ID";
    private final String ACCOUNT_VALUTE_ID = "Valute_ID";

    @Override
    public boolean insert(BankAccount bankAccount) throws DAOException {
        PreparedStatement statement = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(INSERT_INTO);
            statement.setString(1, bankAccount.getName());
            statement.setString(2, bankAccount.getNumber());
            statement.setBoolean(3, bankAccount.isStatus());
            statement.setInt(4, bankAccount.getCountOfMoney());
            statement.setInt(5, bankAccount.getUserId());

            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlCardDAO cardDAO = daoFactory.getCardDAO();

            int id = cardDAO.getValute(bankAccount.getValute());
            statement.setInt(6, id);


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
    public BankAccount find(int id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        BankAccount bankAccount = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString(ACCOUNT_NAME);
                String number = resultSet.getString(ACCOUNT_NUMBER);
                boolean status = resultSet.getBoolean(ACCOUNT_STATUS);
                int money = resultSet.getInt(ACCOUNT_COUNT);
                int userId = resultSet.getInt(ACCOUNT_USER_ID);
                int valuteId = resultSet.getInt(ACCOUNT_VALUTE_ID);

                String valute = DaoFactory.getInstance().getCardDAO().getValute(valuteId);
                bankAccount = Creator.takeBankAccount(id, name, number, status, userId, money, valute);
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
        return bankAccount;
    }

    @Override
    public boolean update(BankAccount bankAccount) throws DAOException {
        PreparedStatement statement = null;


        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(UPDATE_BY_ID);
            statement.setString(1, bankAccount.getName());
            statement.setBoolean(2, bankAccount.isStatus());
            statement.setString(3, bankAccount.getNumber());
            statement.setInt(4, bankAccount.getCountOfMoney());
            statement.setInt(5, bankAccount.getId());

            return statement.execute();

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
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
    public boolean delete(BankAccount bankAccount) throws DAOException {
        PreparedStatement statement = null;


        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(DELETE_BY_NUMBER);
            statement.setString(1, bankAccount.getNumber());

            return statement.execute();

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
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
    public List<BankAccount> getAllByUserID(int id) throws DAOException {
        List<BankAccount> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_USER_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int accountId = resultSet.getInt(ACCOUNT_ID);
                String name = resultSet.getString(ACCOUNT_NAME);
                String number = resultSet.getString(ACCOUNT_NUMBER);
                boolean status = resultSet.getBoolean(ACCOUNT_STATUS);
                int count = resultSet.getInt(ACCOUNT_COUNT);
                int userId = resultSet.getInt(ACCOUNT_USER_ID);
                int valuteId = resultSet.getInt(ACCOUNT_VALUTE_ID);

                DaoFactory daoFactory = DaoFactory.getInstance();
                SqlCardDAO cardDAO = daoFactory.getCardDAO();

                String valute = cardDAO.getValute(valuteId);

                list.add(Creator.takeBankAccount(accountId, name, number, status, userId, count, valute));
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }
        return list;
    }

    @Override
    public BankAccount getByNumber(String Number) throws DAOException {

        BankAccount bankAccount = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_NUMBER);
            statement.setString(1, Number);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                bankAccount = new BankAccount();
                int accountId = resultSet.getInt(ACCOUNT_ID);
                String name = resultSet.getString(ACCOUNT_NAME);
                String number = resultSet.getString(ACCOUNT_NUMBER);
                boolean status = resultSet.getBoolean(ACCOUNT_STATUS);
                int count = resultSet.getInt(ACCOUNT_COUNT);
                int userId = resultSet.getInt(ACCOUNT_USER_ID);
                int valuteId = resultSet.getInt(ACCOUNT_VALUTE_ID);

                String valute = cardDAO.getValute(valuteId);
                bankAccount = Creator.takeBankAccount(accountId,name,number,status,userId, count, valute);
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
                throw new DAOException(e);
            }
        }
        return bankAccount;
    }

    @Override
    public boolean sendMoney(BankAccount bankAccount, Card card, int money) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try {
                statement = connection.prepareStatement(UPDATE_ACCOUNT_A_COUNT_BY_NUMBER);
                statement.setInt(1, money);
                statement.setString(2, bankAccount.getNumber());
                statement.execute();

                int fromAccountValuteId = cardDAO.getValute(bankAccount.getValute());
                int toCardValuteId = cardDAO.getValute(card.getValute());

                float kf = 1;

                if (fromAccountValuteId != toCardValuteId) {
                    statement = connection.prepareStatement(SELECT_COURSE_FROM_EXACHANGE_RATE);
                    statement.setInt(1, fromAccountValuteId);
                    statement.setInt(2, toCardValuteId);
                    resultSet = statement.executeQuery();
                    resultSet.next();

                    kf = resultSet.getInt(1);
                    resultSet.close();
                }

                money *= kf;

                statement = connection.prepareStatement(UPDATE_CARD_A_MONEY_BY_ID);
                statement.setInt(1, money);
                statement.setInt(2, card.getId());
                statement.execute();

                Operation operation = Creator.takeOperation(ACTION_SEND_MONEY, bankAccount.getNumber(), bankAccount.getUserId());

                operationDAO.insert(operation);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DAOException(e.getMessage(), e);
            } finally {
                statement.close();
            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage(), e);
        }
        return false;
    }
}
