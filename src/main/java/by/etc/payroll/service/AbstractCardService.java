package by.etc.payroll.service;

import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Company;
import by.etc.payroll.bean.Rate;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.service.exception.ServiceException;

import java.util.List;

public interface AbstractCardService {
    boolean addCard (String firstName, String lastName, String address, String city, String account, String rate, String company, int userId) throws ServiceException;
    List<Valute> getAllValute() throws ServiceException;
    List<Rate> getAllRate() throws ServiceException;
    List<Company> getAllCompany() throws ServiceException;

    Rate getRateById (int id) throws ServiceException;
    Valute getValuteById (String nameValute) throws ServiceException;
    Company getCompanyById (int id) throws ServiceException;


    Card getCard (String id) throws ServiceException;
    Card getCardByNumber (String number) throws ServiceException;


    boolean checkCardOnBlockByCardId (int cardId) throws ServiceException;
    boolean blockCard (Card card) throws ServiceException;
    boolean unBlockCard (Card card) throws ServiceException;
    boolean doOperation (String action, String accountNumber, int userId) throws ServiceException;

}
