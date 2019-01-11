package by.etc.payroll.service.impl;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.dao.impl.SqlUserDAO;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongNameException;
import by.etc.payroll.service.exception.ServiceWrongNumberException;
import by.etc.payroll.service.util.EventLog;
import by.etc.payroll.service.util.Validator;
import by.etc.payroll.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConcreteBankAccountService implements AbstractBankAccountService {
    private static Logger LOG = LogManager.getLogger(ConcreteBankAccountService.class);
    private final String STATUS = "unlock";
    private final int COUNT = 0;


    @Override
    public List<BankAccount> getCardsByUserID(User user) throws ServiceException {

        List<BankAccount> bankAccountList = new ArrayList<>();

        if (user == null || !user.getRole().equalsIgnoreCase(Roles.USER)) {
            throw new ServiceUnauthorizedAccessException("Incorrect access");
        }

        try {

            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
            bankAccountList = bankAccountDAO.getAllByUserID(user.getId());


        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return bankAccountList;
    }

    @Override
    public BankAccount getCardByNumber(String number) throws ServiceException {

        BankAccount bankAccount = null;
        try {

            if (!Validator.validateString(number)) {

                throw new ServiceWrongNumberException("Incorrect number");
            }


            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
            bankAccount = bankAccountDAO.getByNumber(number);

            if (bankAccount == null) {
                throw new ServiceException();
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return bankAccount;
    }

    @Override
    public boolean addCard(String name, String valute, User user) throws ServiceException {


        if (!Validator.validateUser(user)) {
            throw new ServiceUnauthorizedAccessException();
        }
        if (!Validator.validateString(name)) {
            throw new ServiceWrongNameException("Incorrect name");
        }

        int count = 0;
        boolean status = true;

        try {
            String number = Creator.takeNumberOfBankAccount();

            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();

            while (bankAccountDAO.getByNumber(number) != null) {
                number = Creator.takeNumberOfBankAccount();
            }

            BankAccount bankAccount = Creator.takeBankAccount(name, number, status, user.getId(), count, valute);
            bankAccountDAO.insert(bankAccount);

            EventLog.write("New account", number, user.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return false;
    }

    @Override
    public boolean updateNameAndStatusByNumber(String name, String status, String number) throws ServiceException {

        try {

            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
            boolean upStatus = status.equalsIgnoreCase(STATUS) ? true : false;

            BankAccount bankAccount = bankAccountDAO.getByNumber(number);
            if (bankAccount == null || !Validator.validateString(name)) {

                throw new ServiceWrongNameException("Incorrect number");
            }

            bankAccount.setName(name);
            bankAccount.setStatus(upStatus);

            return bankAccountDAO.update(bankAccount);

        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);

        }
    }

    @Override
    public boolean deleteCard(String number, User user) throws ServiceException {
        if (user == null || !user.getRole().equalsIgnoreCase(Roles.USER)) {
            throw new ServiceUnauthorizedAccessException();
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlUserDAO userDAO = daoFactory.getUserDAO();


        if (!Validator.validateNumber(number)) {
            throw new ServiceException();
        }

        try {
            BankAccount bankAccount = bankAccountDAO.getByNumber(number);
            user = userDAO.findByLogin(user.getLogin());

            if (user.getId() != bankAccount.getUserId()) {
                throw new ServiceException();
            }

            return bankAccountDAO.delete(bankAccount);

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public BankAccount getAccountById(int accountId) throws ServiceException {
        try {
            return DaoFactory.getInstance().getBankAccountDAO().find(accountId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
