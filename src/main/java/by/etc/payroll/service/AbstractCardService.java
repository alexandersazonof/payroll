package by.etc.payroll.service;

import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Company;
import by.etc.payroll.bean.Rate;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.service.exception.ServiceException;

import java.util.List;

public interface AbstractCardService {
    boolean addCard (String firstName, String lastName, String address, String city, String account, String rate, String valute, String company, int userId) throws ServiceException;
    List<Valute> getValute () throws ServiceException;
    List<Rate> getRate () throws ServiceException;
    List<Company> getCompany() throws ServiceException;

    Rate getRateById (int id) throws ServiceException;
    Valute getValuteById (String nameValute) throws ServiceException;
    Company getCompanyById (int id) throws ServiceException;


    Card getCard (String id) throws ServiceException;
}
