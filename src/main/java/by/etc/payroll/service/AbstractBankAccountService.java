package by.etc.payroll.service;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.service.exception.ServiceException;

import java.util.List;

public interface AbstractBankAccountService {
    List<BankAccount> getCardsByUserID (User user)  throws ServiceException;
    BankAccount getCardByNumber (String number) throws ServiceException;
    boolean addCard (String name, String valute, User user) throws ServiceException;
    boolean updateNameAndStatusByNumber (String name, String status, String number) throws ServiceException;
    boolean deleteCard (String number, User user) throws ServiceException;
    BankAccount getAccountById (int accountId) throws ServiceException;

    boolean blockAccount (String bankAccountNumber, User user) throws ServiceException;
    boolean unBlockAccount (String bankAccountNumber, User user) throws ServiceException;
}
