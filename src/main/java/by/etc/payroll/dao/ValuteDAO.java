package by.etc.payroll.dao;

import by.etc.payroll.bean.ExchangeRate;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface ValuteDAO extends CrudDAO<Valute>{
    List<Valute> getAll() throws DAOException;
    int getIdByName (String name) throws DAOException;
    List<ExchangeRate> getAllExchangeRate() throws DAOException;

    ExchangeRate getExchangeRateById (int id) throws DAOException;
    boolean updateExchangeRateById (ExchangeRate exchangeRate) throws DAOException;
}
