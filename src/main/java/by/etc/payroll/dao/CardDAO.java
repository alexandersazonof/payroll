package by.etc.payroll.dao;

import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Operation;
import by.etc.payroll.bean.UserData;
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
}
