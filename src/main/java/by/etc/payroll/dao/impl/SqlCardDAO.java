package by.etc.payroll.dao.impl;

import by.etc.payroll.bean.*;
import by.etc.payroll.dao.CardDAO;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.service.creator.Creator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlCardDAO implements CardDAO<Card>{
    private Logger LOG = LogManager.getLogger(SqlCardDAO.class);

    private final String SELECT_BY_ACCOUNT_ID = "select * from cards where id_account = ?";

    private final String INSERT_INTO_OPERATION = "insert into operation (action, date, account_number, user_id) values (?, ?, ?, ?);";


    private final String SELECT_BLOCK_CARD_BY_CARD_ID = "select * from block_cards where card_id = ?";
    private final String SELECT_CARD_BY_NUMBER = "select * from cards where number = ?";
    private final String SELECT_VALUTE_BY_CARD_ID = "select * from valute where id = ?";
    private final String SELECT_VALUTE_BY_NAME = "select * from valute where name = ?";
    private final String SELECT_CARD_BY_ID = "select * from cards where id = ?";

    private final String INSERT_USER_DATA = "insert into user_data (first_name, last_name, address, city, id_card) values (?, ?, ?, ?, ?)";
    private final String INSERT_CARD = "insert into cards (number, valid_thru, customer, company_id, id_account, rate_id, money, id_valute) values (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String INSERT_BLOCK_CARD = "insert into block_cards (card_id) values (?)";
    private final String DELETE_FROM_BLOCK_CARD = "delete from block_cards where card_id = ?";

    private final String DELETE_FROM_CARD_BY_ID = "delete from cards where id = ?";
    private final String UPDATE_MONEY_IN_ACCOUNT_BY_ID = "update account set count = count + ? where id = ?";

    private final String SELECT_COURSE_FROM_EXACHANGE_RATE = "select course from exachange_rates where from_valute_id = ? and to_valute_id = ?";
    private final String UPDATE_MONEY_IN_CARD_BY_ID = "update cards set money = money + ? where id = ?";
    private final String SELECT_ALL_FROM_TRANSFER = "select * from transfer";


    private final String CARD_ID = "id";
    private final String CARD_NUMBER= "number";
    private final String CARD_VALID_THRU = "valid_thru";
    private final String CARD_CUSTOMER = "customer";
    private final String CARD_COMPANY = "company_id";
    private final String CARD_ID_ACCOUNT = "id_account";
    private final String CARD_RATE = "rate_id";
    private final String CARD_MONEY = "money";
    private final String CARD_ID_VALUTE = "id_valute";

    private final String TRANSFER_ID = "id";
    private final String TRANSFER_FROM_CARD = "from_card_id";
    private final String TRANSFER_TO_CARD = "to_card_id";
    private final String TRANSFER_MONEY = "money";

    private final String VALUTE_NAME = "name";
    private final String VALUTE_ID = "id";

    @Override
    public List<Card> getAllByAccountId(int id) throws DAOException {
        List<Card> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int cardId = resultSet.getInt(CARD_ID);
                String number = resultSet.getString(CARD_NUMBER);
                String validThru = resultSet.getString(CARD_VALID_THRU);
                String customer = resultSet.getString(CARD_CUSTOMER);
                int company = resultSet.getInt(CARD_COMPANY);
                int accountId = resultSet.getInt(CARD_ID_ACCOUNT);
                int rate = resultSet.getInt(CARD_RATE);
                int money = resultSet.getInt(CARD_MONEY);

                String valute = getValute(resultSet.getInt(CARD_ID_VALUTE));

                list.add(create(cardId, number,
                        validThru, customer, company, accountId, rate, money, valute));
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
    public String getValute(int idValut) throws DAOException {
        String valut = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_VALUTE_BY_CARD_ID);
            statement.setInt(1, idValut);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
               valut = resultSet.getString(VALUTE_NAME);
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
        return valut;
    }

    @Override
    public int getValute(String nameValute) throws DAOException {
        int id = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection() ) {

            statement = connection.prepareStatement(SELECT_VALUTE_BY_NAME);
            statement.setString(1, nameValute);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(VALUTE_ID);
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
        return id;
    }

    @Override
    public boolean addCard(Card card, Operation operation, UserData userData) throws DAOException {
        PreparedStatement statement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try {

                statement = connection.prepareStatement(INSERT_CARD);
                statement.setString(1, card.getNumber());
                statement.setString(2, card.getDate());
                statement.setString(3, card.getCustomer());
                statement.setInt(4, card.getCompany());
                statement.setInt(5, card.getIdAccount());
                statement.setInt(6, card.getRate());
                statement.setInt(7, card.getMoney());
                statement.setInt(8, getValute(card.getValute()));

                statement.execute();

                connection.commit();
                card = getByCardNumber(card.getNumber());


                statement = connection.prepareStatement(INSERT_USER_DATA);
                statement.setString(1, userData.getFirstName());
                statement.setString(2, userData.getLastName());
                statement.setString(3, userData.getAddress());
                statement.setString(4, userData.getCity());
                statement.setInt(5, card.getId());
                statement.execute();


                statement = connection.prepareStatement(INSERT_INTO_OPERATION);
                statement.setString(1, operation.getAction());
                statement.setString(2, operation.getDate());
                statement.setString(3, operation.getNumber());
                statement.setInt(4, operation.getUserId());

                statement.execute();

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new DAOException(e.getMessage(), e);
            } finally {
                statement.close();
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return false;
    }

    @Override
    public Card getByCardNumber(String number) throws DAOException {
        Card card = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_CARD_BY_NUMBER);
            statement.setString(1, number);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                int id = resultSet.getInt(CARD_ID);
                String validThru = resultSet.getString(CARD_VALID_THRU);
                String customer = resultSet.getString(CARD_CUSTOMER);
                int company = resultSet.getInt(CARD_COMPANY);
                int accountId = resultSet.getInt(CARD_ID_ACCOUNT);
                int rate = resultSet.getInt(CARD_RATE);
                int money = resultSet.getInt(CARD_MONEY);
                String valute = getValute(resultSet.getInt(CARD_ID_VALUTE));

                card = Creator.takeCard(id, number, validThru, customer, company, accountId, rate, money, valute);

            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return card;
    }

    @Override
    public boolean isBlock(int idCard) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            statement = connection.prepareStatement(SELECT_BLOCK_CARD_BY_CARD_ID);
            statement.setInt(1, idCard);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }

        return false;
    }

    @Override
    public boolean blockCard(int idCard) throws DAOException {
        PreparedStatement statement = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            statement = connection.prepareStatement(INSERT_BLOCK_CARD);
            statement.setInt(1, idCard);


            return statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean clearCard(int idCard) throws DAOException {
        PreparedStatement statement = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            statement = connection.prepareStatement(DELETE_FROM_BLOCK_CARD);
            statement.setInt(1, idCard);


            return statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteCard(Card card, BankAccount bankAccount, Operation operation) throws DAOException {
        PreparedStatement statement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            try {
                connection.setAutoCommit(false);

                statement = connection.prepareStatement(DELETE_FROM_CARD_BY_ID);
                statement.setInt(1, card.getId());
                statement.execute();

                statement = connection.prepareStatement(UPDATE_MONEY_IN_ACCOUNT_BY_ID);
                statement.setInt(1, card.getMoney());
                statement.setInt(2, bankAccount.getId());
                statement.execute();

                statement = connection.prepareStatement(INSERT_INTO_OPERATION);
                statement.setString(1, operation.getAction());
                statement.setString(2, operation.getDate());
                statement.setString(3, operation.getNumber());
                statement.setInt(4, operation.getUserId());
                statement.execute();

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new DAOException(e.getMessage(), e);
            }



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
    public boolean transefreMoney(Card fromNumber, Card toNumber, int money) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        float course = 1;

        int fromCardValuteId = getValute(fromNumber.getValute());
        int toCardValuteId = getValute(toNumber.getValute());

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try {
                if (fromCardValuteId != toCardValuteId) {
                    statement = connection.prepareStatement(SELECT_COURSE_FROM_EXACHANGE_RATE);

                    statement.setInt(1, fromCardValuteId);
                    statement.setInt(2, toCardValuteId);
                    resultSet = statement.executeQuery();
                    resultSet.next();
                    course = resultSet.getFloat(1);
                    statement.close();
                    resultSet.close();
                }

                statement = connection.prepareStatement(UPDATE_MONEY_IN_CARD_BY_ID);

                statement.setInt(1, money * (-1));
                statement.setInt(2, fromNumber.getId());
                statement.execute();
                statement.close();


                int moneyByCourse = (int)(money * course);
                statement = connection.prepareStatement(UPDATE_MONEY_IN_CARD_BY_ID);
                statement.setInt(1, moneyByCourse);
                statement.setInt(2, toNumber.getId());
                statement.execute();
                statement.close();


                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new DAOException(e.getMessage(), e);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public List<Transfer> getAllTransfer() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Transfer> transferList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            statement = connection.prepareStatement(SELECT_ALL_FROM_TRANSFER);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(TRANSFER_ID);
                int fromCard = resultSet.getInt(TRANSFER_FROM_CARD);
                int toCard = resultSet.getInt(TRANSFER_TO_CARD);
                int money = resultSet.getInt(TRANSFER_MONEY);

                Transfer transfer = Creator.takeTransfer(id, fromCard, toCard, money);
                transferList.add(transfer);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }

        return transferList;
    }


    @Override
    public boolean insert(Card card) throws DAOException {
        return false;
    }

    @Override
    public Card find(int id) throws DAOException {
        Card card = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_CARD_BY_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                String number = resultSet.getString(CARD_NUMBER);
                String validThru = resultSet.getString(CARD_VALID_THRU);
                String customer = resultSet.getString(CARD_CUSTOMER);
                int company = resultSet.getInt(CARD_COMPANY);
                int accountId = resultSet.getInt(CARD_ID_ACCOUNT);
                int rate = resultSet.getInt(CARD_RATE);
                int money = resultSet.getInt(CARD_MONEY);
                String valute = getValute(resultSet.getInt(CARD_ID_VALUTE));

                card = Creator.takeCard(id, number, validThru, customer, company, accountId, rate, money, valute);

            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                statement.close();
                resultSet.close();
                connection.close();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return card;
    }

    @Override
    public boolean update(Card card) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Card card) throws DAOException {
        PreparedStatement statement = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            statement = connection.prepareStatement(DELETE_FROM_CARD_BY_ID);
            statement.setInt(1, card.getId());
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


    private Card create (int id, String number,
                         String date, String customer,
                         int company, int idAccount, int rate,
                         int money, String valute) {
        Card card = new Card();
        card.setId(id);
        card.setCompany(company);
        card.setCustomer(customer);
        card.setDate(date);
        card.setNumber(number);
        card.setIdAccount(idAccount);
        card.setRate(rate);
        card.setMoney(money);
        card.setValute(valute);

        return card;
    }
}
