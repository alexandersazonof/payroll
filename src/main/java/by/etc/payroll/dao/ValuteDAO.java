package by.etc.payroll.dao;

import by.etc.payroll.bean.Valute;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface ValuteDAO extends CrudDAO<Valute>{
    List<Valute> getAll() throws DAOException;
    int getIdByName (String name) throws DAOException;
}
