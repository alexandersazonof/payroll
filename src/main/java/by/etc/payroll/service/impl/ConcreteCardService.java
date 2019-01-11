package by.etc.payroll.service.impl;

import by.etc.payroll.bean.*;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.*;
import by.etc.payroll.service.AbstractCardService;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.util.Validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ConcreteCardService implements AbstractCardService {
    private final String ACTION_OPERATION = "Add new card : ";


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
}
