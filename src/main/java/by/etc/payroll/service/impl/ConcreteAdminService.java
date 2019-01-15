package by.etc.payroll.service.impl;

import by.etc.payroll.bean.*;
import by.etc.payroll.command.util.Message;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.dao.impl.SqlCardDAO;
import by.etc.payroll.dao.impl.SqlRateDAO;
import by.etc.payroll.service.AbstractAdminService;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceWrongNameException;
import by.etc.payroll.service.util.Validator;

import java.util.List;

public class ConcreteAdminService implements AbstractAdminService {
    @Override
    public List<User> getAllUserWithoutPassword() throws ServiceException {
        try {
            return DaoFactory.getInstance().getUserDAO().getAllUserWithoutPassword();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BankAccount> getAllBankAccount() throws ServiceException {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
            SqlCardDAO cardDAO = daoFactory.getCardDAO();

            List<BankAccount> bankAccountList = bankAccountDAO.getAll();
            for (BankAccount bankAccount :bankAccountList) {
                bankAccount.setCardList(cardDAO.getAllByAccountId(bankAccount.getId()));
            }


            return bankAccountList;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Transfer> getAllTransfer() throws ServiceException {
        try {
            return DaoFactory.getInstance().getCardDAO().getAllTransfer();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Operation> getAllOperation() throws ServiceException {
        try {
            return DaoFactory.getInstance().getOperationDAO().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Rate checkRate(String name, String description) throws ServiceException {
        if (!Validator.validateString(name) || !Validator.validateString(description)) {
            throw new ServiceQueryException(Message.INCORRECT_QUERY);
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlRateDAO rateDAO = daoFactory.getRateDAO();
        try {
            int id = rateDAO.getIdByName(name);

            if (id == 0) {
                throw new ServiceQueryException(Message.INCORRECT_QUERY);
            }

            return rateDAO.find(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateRate(String name, String description) throws ServiceException {
        if (!Validator.validateString(name) || !Validator.validateString(description)) {
            throw new ServiceWrongNameException(Message.INCORRECT_VALUE);
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlRateDAO rateDAO = daoFactory.getRateDAO();

        try {
            int id = rateDAO.getIdByName(name);

            if (id == 0) {
                throw new ServiceQueryException(Message.INCORRECT_QUERY);
            }

            Rate rate = rateDAO.find(id);


            rate.setName(name);
            rate.setDescription(description);

            rateDAO.update(rate);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean addRate(String name, String description) throws ServiceException {
        if (!Validator.validateString(name) || !Validator.validateString(description)) {
            throw new ServiceWrongNameException(Message.INCORRECT_VALUE);
        }

        Rate rate = Creator.takeRate(name, description);

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlRateDAO rateDAO = daoFactory.getRateDAO();

        try {
            return rateDAO.insert(rate);
        } catch (DAOException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteRate(String name) throws ServiceException {
        if (!Validator.validateString(name)) {
            throw new ServiceQueryException(Message.INCORRECT_QUERY);
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlRateDAO rateDAO = daoFactory.getRateDAO();

        Rate rate = Creator.takeRate(name, "");

        try {
            return rateDAO.delete(rate);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
