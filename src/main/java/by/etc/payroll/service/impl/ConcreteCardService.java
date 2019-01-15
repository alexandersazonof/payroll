package by.etc.payroll.service.impl;

import by.etc.payroll.bean.*;
import by.etc.payroll.command.util.Message;
import by.etc.payroll.command.util.UserUtil;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.*;
import by.etc.payroll.service.AbstractCardService;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.util.Validator;
import org.mindrot.jbcrypt.BCrypt;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConcreteCardService implements AbstractCardService {
    private final String ACTION_OPERATION = "Add new card : ";
    private final String ACTION_SEND_MONEY_FROM = "Send money from : ";
    private final String ACTION_GET_MONEY_TO = "Get money to : ";


    @Override
    public boolean addCard(String firstName, String lastName, String address, String city, String account, String rate, String company, int userId) throws ServiceException {
        if (!Validator.validateString(firstName) || !Validator.validateString(lastName)
                || !Validator.validateString(address) || !Validator.validateString(city)
                || !Validator.validateNumber(account) || !Validator.validateString(rate)) {
            throw new ServiceWrongNameException("incorrect value");
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlRateDAO rateDAO = daoFactory.getRateDAO();
        SqlValuteDAO valuteDAO = daoFactory.getValuteDAO();
        SqlCompanyDAO companyDAO = daoFactory.getCompanyDAO();

        try {
            BankAccount bankAccount = bankAccountDAO.getByNumber(account);

            if (!bankAccount.isStatus()) {
                throw new ServiceBlockAccountException("Account block");
            }
            int idBankAccount = bankAccount.getId();
            int idRate = rateDAO.getIdByName(rate);

            int idCompany = companyDAO.getIdByName(company);
            String customer = Creator.getCustomer(firstName, lastName);


            String validDate = Creator.getDateForCard();


            String number = Creator.takeNumberOfCards();

            int money = 0;

            UserData userData = Creator.takeUserData(firstName, lastName, address, city,1);
            Card card = Creator.takeCard(number, validDate, customer, idCompany, idBankAccount, idRate, money, bankAccount.getValute());
            Operation operation = Creator.takeOperation(ACTION_OPERATION + number, account, userId);


            SqlCardDAO cardDAO = daoFactory.getCardDAO();

            cardDAO.addCard(card, operation, userData);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return false;
    }

    @Override
    public List<Valute> getAllValute() throws ServiceException {
        try {
            return DaoFactory.getInstance().getValuteDAO().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Rate> getAllRate() throws ServiceException {
        try {
            return DaoFactory.getInstance().getRateDAO().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<Company> getAllCompany() throws ServiceException {
        try {
            return DaoFactory.getInstance().getCompanyDAO().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Operation> getAllOperationByNumber(String number) throws ServiceException {
        try {
            return DaoFactory.getInstance().getOperationDAO().getAllByNumber(number);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<ExchangeRate> getAllExchangeRate() throws ServiceException {
        try {
            return DaoFactory.getInstance().getValuteDAO().getAllExchangeRate();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ExchangeRate getExchangeRateById(String exchangeId) throws ServiceException {

        try {
            if (exchangeId == null) {
                throw new ServiceQueryException(Message.INCORRECT_QUERY);
            }

            int id = Integer.valueOf(exchangeId);

            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlValuteDAO valuteDAO = daoFactory.getValuteDAO();

            return valuteDAO.getExchangeRateById(id);
        } catch (NumberFormatException e) {
            throw new ServiceQueryException(Message.INCORRECT_QUERY);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String getValuteNameById(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getCardDAO().getValute(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean editValite(String exchangeId, String course) throws ServiceException {
        if (!Validator.validateString(exchangeId) || !Validator.validateString(course)) {
            throw new ServiceQueryException(Message.INCORRECT_QUERY);
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlValuteDAO valuteDAO = daoFactory.getValuteDAO();

        try {
            int id = Integer.valueOf(exchangeId);
            float todayCourse = Float.valueOf(course);

            ExchangeRate exchangeRate = valuteDAO.getExchangeRateById(id);

            if (exchangeRate == null) {
                throw new ServiceQueryException(Message.INCORRECT_QUERY);
            }

            exchangeRate.setCourse(todayCourse);
            return valuteDAO.updateExchangeRateById(exchangeRate);

        } catch (NumberFormatException e) {
            throw new ServiceQueryException(Message.INCORRECT_QUERY);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Rate getRateById(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getRateDAO().find(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Valute getValuteById(String nameValute) throws ServiceException {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlValuteDAO valuteDAO = daoFactory.getValuteDAO();
            int idValute = valuteDAO.getIdByName(nameValute);


            return valuteDAO.find(idValute);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Company getCompanyById(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getCompanyDAO().find(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Card getCard(String cardId) throws ServiceException {
        if (!Validator.validateString(cardId)) {
            throw new ServiceWrongCardNumber("Incorrect id");
        }

        try {
            int id = Integer.valueOf(cardId);
            DaoFactory daoFactory  = DaoFactory.getInstance();
            SqlCardDAO cardDAO = daoFactory.getCardDAO();

            Card card = cardDAO.find(id);

            if (card == null) {
                throw new ServiceWrongCardNumber("Incorrect id");
            }

            return card;

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Card getCardByNumber(String number) throws ServiceException {
        try {
            return DaoFactory.getInstance().getCardDAO().getByCardNumber(number);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean checkCardOnBlockByCardId(int cardId) throws ServiceException {
        try {
            return !DaoFactory.getInstance().getCardDAO().isBlock(cardId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean blockCard(Card card) throws ServiceException {
        if (card == null) {
            throw new ServiceQueryException("Incorrect query");
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

        try {
            boolean nowStatus = cardDAO.isBlock(card.getId());
            if (nowStatus == true) {
                throw new ServiceQueryException("Incorrect query");
            }

            cardDAO.blockCard(card.getId());

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }


        return false;
    }

    @Override
    public boolean unBlockCard(Card card) throws ServiceException {
        if (card == null) {
            throw new ServiceQueryException("Incorrect query");
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

        try {
            boolean nowStatus = cardDAO.isBlock(card.getId());
            if (nowStatus == false) {
                throw new ServiceQueryException("Incorrect query");
            }

            cardDAO.clearCard(card.getId());

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }


        return false;
    }

    @Override
    public boolean doOperation(String action, String accountNumber, int userId) throws ServiceException {
        Operation operation = Creator.takeOperation(action, accountNumber, userId);
        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

        try {
            operationDAO.insert(operation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean deleteCard(Card card, BankAccount bankAccount, Operation operation) throws ServiceException {
        try {
            return DaoFactory.getInstance().getCardDAO().deleteCard(card, bankAccount, operation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Card> getAllCardByUser(User user) throws ServiceException {
        UserUtil.isUser(user);
        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        List<Card> cardList = new ArrayList<>();

        try {
            List<BankAccount> bankAccountList = bankAccountDAO.getAllByUserID(user.getId());


            for (BankAccount b :bankAccountList) {
                List<Card> tempCardList = cardDAO.getAllByAccountId(b.getId());
                cardList.addAll(tempCardList);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return cardList;
    }

    @Override
    public boolean sendMoney(User user, String fromNumber, String toNumber, String money, String password) throws ServiceException {
        UserUtil.isUser(user);

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlUserDAO userDAO = daoFactory.getUserDAO();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();

        try {
            User userWithPassword = userDAO.findByLogin(user.getLogin());
            if (!BCrypt.checkpw(password, userWithPassword.getPassword())) {
                throw new ServiceWrongPasswordException("Incorrect password");
            }

            Card fromCard = cardDAO.getByCardNumber(fromNumber);
            Card toCard = cardDAO.getByCardNumber(toNumber);

            if (cardDAO.isBlock(fromCard.getId())) {
                throw new ServiceBlockAccountException(fromCard.getNumber());
            }
            if (cardDAO.isBlock(toCard.getId())) {
                throw new ServiceBlockAccountException(toCard.getNumber());
            }

            if (fromCard == null || toCard == null) {
                throw new ServiceWrongCardNumber("Incorrect card number");
            }


            int count = 0;
            try {
                count = Integer.parseInt(money);
            } catch (NumberFormatException e) {
                throw new ServiceWrongCountException(e.getMessage(), e);
            }

            if (fromCard.getMoney() < count) {
                throw new ServiceWrongCountException("Incorrect count");
            }

            cardDAO.transefreMoney(fromCard, toCard, count);

            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();

            BankAccount fromBankAccount = bankAccountDAO.find(fromCard.getIdAccount());
            BankAccount toBankAccount = bankAccountDAO.find(toCard.getIdAccount());

            SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

            Operation fromCardOperation = Creator.takeOperation(ACTION_SEND_MONEY_FROM + fromCard.getNumber() + " " + money + " " + fromCard.getValute(),
                    fromBankAccount.getNumber(), user.getId());
            Operation toCardOperation = Creator.takeOperation(ACTION_GET_MONEY_TO + toCard.getNumber() + " " + money + " " + toCard.getValute(),
                    toBankAccount.getNumber(), toBankAccount.getUserId());

            operationDAO.insert(fromCardOperation);
            operationDAO.insert(toCardOperation);

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return true;
    }
}
