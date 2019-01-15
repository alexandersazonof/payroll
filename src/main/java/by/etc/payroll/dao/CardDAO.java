package by.etc.payroll.dao;

import by.etc.payroll.bean.*;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface CardDAO<Card> extends CrudDAO<Card> {

    List<Card> getAllByAccountId (int id) throws DAOException ;
    String getValute(int idValute) throws DAOException ;
    int getValute(String nameValute) throws DAOException ;

    boolean addCard (Card card, Operation operation, UserData userData) throws DAOException;
    Card getByCardNumber (String number) throws DAOException;

    boolean isBlock (int idCard) throws DAOException;
    boolean blockCard (int idCard) throws DAOException;
    boolean clearCard (int idCard) throws DAOException;

    boolean deleteCard (Card card , BankAccount bankAccount, Operation operation) throws DAOException;

    boolean transefreMoney (Card fromNumber, Card toNumber, int money) throws DAOException;
    List<Transfer> getAllTransfer() throws DAOException;
}
