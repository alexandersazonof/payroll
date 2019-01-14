package by.etc.payroll.service.impl;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.dao.impl.SqlCardDAO;
import by.etc.payroll.service.AbstractAdminService;
import by.etc.payroll.service.exception.ServiceException;

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
}
