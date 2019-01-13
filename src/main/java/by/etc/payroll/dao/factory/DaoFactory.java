package by.etc.payroll.dao.factory;

import by.etc.payroll.dao.UserDAO;
import by.etc.payroll.dao.impl.*;

public class DaoFactory {
    private static DaoFactory INSTANCE ;

    private SqlUserDAO userDAO = new SqlUserDAO();
    private SqlBankAccountDAO bankAccountDAO = new SqlBankAccountDAO();
    private SqlCardDAO cardDAO = new SqlCardDAO();
    private SqlOperationDAO operationDAO = new SqlOperationDAO();
    private SqlRateDAO rateDAO = new SqlRateDAO();
    private SqlValuteDAO valuteDAO = new SqlValuteDAO();
    private SqlCompanyDAO companyDAO = new SqlCompanyDAO();
    private SqlUserDataDAO userDataDAO = new SqlUserDataDAO();
    private SqlApplicationDAO applicationDAO = new SqlApplicationDAO();

    private DaoFactory(){}

    public static DaoFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DaoFactory();
        }
        return INSTANCE;
    }

    public SqlUserDAO getUserDAO () {
        return userDAO;
    }

    public SqlBankAccountDAO getBankAccountDAO() {
        return bankAccountDAO;
    }

    public SqlCardDAO getCardDAO() {
        return cardDAO;
    }

    public SqlOperationDAO getOperationDAO() {
        return operationDAO;
    }

    public SqlRateDAO getRateDAO() {
        return rateDAO;
    }

    public SqlValuteDAO getValuteDAO() {
        return valuteDAO;
    }

    public SqlCompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    public SqlUserDataDAO getUserDataDAO() {
        return userDataDAO;
    }

    public SqlApplicationDAO getApplicationDAO() {return applicationDAO; }
}
