package by.etc.payroll.service.impl;

import by.etc.payroll.bean.*;
import by.etc.payroll.controller.command.util.Message;
import by.etc.payroll.controller.command.util.UserUtil;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.impl.*;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.util.EventLog;
import by.etc.payroll.service.util.Validator;
import by.etc.payroll.service.util.Roles;

import java.util.ArrayList;
import java.util.List;

public class ConcreteBankAccountService implements AbstractBankAccountService {
    private final boolean BLOCK_ACCOUNT = false;
    private final String ACTION_BLOCK_ACCOUNT = "Block account";
    private final String ACTION_UNBLOCK_ACCOUNT = "Application send to Admin";
    private final String APPLICATION_FOR_UNBLOCK = "Unblock my account";
    private final String ACTION_DELETE_ACCOUNT = "Delete account";

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

        BankAccount bankAccount = new BankAccount();
        try {

            if (!Validator.validateString(number)) {
                throw new ServiceQueryException("Incorrect number");
            }


            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
            bankAccount = bankAccountDAO.getByNumber(number);

            if (bankAccount == null) {
                throw new ServiceQueryException("Incorrect number");
            }

            SqlCardDAO cardDAO = daoFactory.getCardDAO();
            List<Card> cardList = cardDAO.getAllByAccountId(bankAccount.getId());

            bankAccount.setCardList(cardList);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return bankAccount;
    }

    @Override
    public boolean addCard(String name, String valute, User user) throws ServiceException {


        UserUtil.isUser(user);
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

            System.out.println(bankAccount);
            EventLog.write("New account", number, user.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return false;
    }


    @Override
    public BankAccount getAccountById(int accountId) throws ServiceException {
        try {
            return DaoFactory.getInstance().getBankAccountDAO().find(accountId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean blockAccount(String bankAccountNumber, User user) throws ServiceException {
        if (!Validator.validateString(bankAccountNumber)) {
            throw new ServiceQueryException("Incorrect number");
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();
        try {
            BankAccount bankAccount = bankAccountDAO.getByNumber(bankAccountNumber);

            if (bankAccount == null) {
                throw new ServiceQueryException("Incorrect number");
            }

            UserUtil.isAccountOfUser(user, bankAccount);

            bankAccount.setStatus(BLOCK_ACCOUNT);
            bankAccountDAO.update(bankAccount);

            List<Card> cardList = cardDAO.getAllByAccountId(bankAccount.getId());

            for (Card card :cardList) {
                if (!cardDAO.isBlock(card.getId())) {
                    cardDAO.blockCard(card.getId());
                }
            }

            Operation operation = Creator.takeOperation(ACTION_BLOCK_ACCOUNT, bankAccountNumber, user.getId());
            operationDAO.insert(operation);

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean unBlockAccount(String bankAccountNumber, User user) throws ServiceException {
        if (!Validator.validateString(bankAccountNumber)) {
            throw new ServiceQueryException("Incorrect number");
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();
        SqlApplicationDAO applicationDAO = daoFactory.getApplicationDAO();

        try {
            BankAccount bankAccount = bankAccountDAO.getByNumber(bankAccountNumber);

            if (bankAccount == null) {
                throw new ServiceQueryException("Incorrect number");
            }

            UserUtil.isAccountOfUser(user, bankAccount);

            Application application = Creator.takeApplication(APPLICATION_FOR_UNBLOCK, bankAccount.getId());
            applicationDAO.insert(application);

            Operation operation = Creator.takeOperation(ACTION_UNBLOCK_ACCOUNT, bankAccountNumber, user.getId());
            operationDAO.insert(operation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean deleteBankAccount(BankAccount bankAccount, User user) throws ServiceException {
        UserUtil.isAccountOfUser(user, bankAccount);

        if (bankAccount.getCountOfMoney() > 0) {
            throw new ServiceWrongCountException(Message.INCORRECT_MONEY);
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

        try {

            for (Card card :bankAccount.getCardList()) {
                System.out.println(card);
                if (card.getMoney() > 0) {
                    throw new ServiceWrongCountException(Message.INCORRECT_MONEY);
                }
            }

            bankAccountDAO.delete(bankAccount);

            for (Card card : bankAccount.getCardList()) {
                cardDAO.delete(card);
            }

            Operation operation = Creator.takeOperation(ACTION_DELETE_ACCOUNT, bankAccount.getNumber(), user.getId());
            operationDAO.insert(operation);


        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean sendMoney(User user, String accountNumber, String cardNumber, String money) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
        SqlCardDAO cardDAO = daoFactory.getCardDAO();
        try {
            BankAccount bankAccount = bankAccountDAO.getByNumber(accountNumber);

            if (bankAccount == null) {
                throw new ServiceQueryException(Message.INCORRECT_QUERY);
            }
            UserUtil.isAccountOfUser(user, bankAccount);

            Card card = cardDAO.getByCardNumber(cardNumber);

            if (card == null) {
                throw new ServiceQueryException(Message.INCORRECT_QUERY);
            }

            int accountMoney = Integer.valueOf(money);

            if (accountMoney > bankAccount.getCountOfMoney() || accountMoney < 0) {
                throw new ServiceWrongCountException(Message.INCORRECT_MONEY);
            }


            bankAccountDAO.sendMoney(bankAccount, card, accountMoney);

        } catch (NumberFormatException e) {
            throw new ServiceWrongCountException(Message.INCORRECT_MONEY);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Operation> searchByWord(String accountNumber, String keyWord) throws ServiceException {

        List<Operation> operationList = new ArrayList<>();
        DaoFactory daoFactory = DaoFactory.getInstance();

        SqlOperationDAO operationDAO = daoFactory.getOperationDAO();


        try {
            if (!Validator.validateString(keyWord)) {
                operationList = operationDAO.getAllByNumber(accountNumber);
            } else {
                operationList = operationDAO.getAllByNumberAndKeyWord(accountNumber, keyWord);

            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return operationList;
    }
}

