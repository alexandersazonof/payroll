package by.etc.payroll.service.impl;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Transaction;
import by.etc.payroll.bean.User;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.dao.impl.SqlTransactionDAO;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.AbstractTransactionService;
import by.etc.payroll.service.util.Validator;
import by.etc.payroll.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConcreteTransactionService implements AbstractTransactionService {
    private final String FORMAT = "yyyy-MM-dd";

    @Override
    public boolean insertTransaction(String fromAccount, String toAccount, String count) throws ServiceException {

        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();

            BankAccount fromCard = bankAccountDAO.getByNumber(fromAccount);
            if (!Validator.validateNumber(fromAccount) || fromCard == null) {
                throw new ServiceWrongNumberException("Incorrect from account");
            }

            BankAccount toCard = bankAccountDAO.getByNumber(toAccount);
            if (!Validator.validateNumber(toAccount) || toCard == null) {
                throw new ServiceWrongNumberException("Incorrect to account");
            }

            if (!Validator.validateString(count)) {
                throw new ServiceWrongCountException("Incorrect count");
            }

            if (!toCard.isStatus() || !fromCard.isStatus()) {
                throw new ServiceBlockAccountException("Block account");
            }

            int countOfMoney = 0;

            try {
                countOfMoney = Integer.valueOf(count);
            } catch (NumberFormatException e) {
                throw new ServiceWrongCountException(e.getMessage(), e);
            }


            if (countOfMoney <= 0 || fromCard.getCountOfMoney() < countOfMoney) {
                throw new ServiceWrongCountException("Incorrect count");
            }

            SqlTransactionDAO transactionDAO = daoFactory.getTransactionDAO();
            return transactionDAO.doTransacation(create(fromAccount, toAccount, countOfMoney));

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Transaction> showHistory(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Incorrect user");
        }

        if (!user.getRole().equalsIgnoreCase(Roles.USER)) {
            throw new ServiceUnauthorizedAccessException("Error access");
        }

        try {
        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlTransactionDAO dao = daoFactory.getTransactionDAO();


            return dao.getAllByUserId(user.getId());
        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Transaction create (String fromAccount, String toAccount, int count) {
        Transaction transaction = new Transaction();
        transaction.setCount(count);
        transaction.setFromNumber(fromAccount);
        transaction.setToNumber(toAccount);
        SimpleDateFormat dmyFormat = new SimpleDateFormat(FORMAT);
        transaction.setDate(dmyFormat.format(new Date()));
        return transaction;
    }
}
