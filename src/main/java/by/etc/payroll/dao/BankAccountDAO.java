package by.etc.payroll.dao;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.dao.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

public interface BankAccountDAO<BankAccount> extends CrudDAO<BankAccount> {

    List<BankAccount> getAllByUserID  (int id) throws DAOException;
    BankAccount getByNumber (String Number) throws DAOException;

    boolean sendMoney (BankAccount bankAccount, Card card, int money) throws DAOException;
}
