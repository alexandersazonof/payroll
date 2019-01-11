package by.etc.payroll.dao;

import by.etc.payroll.bean.Rate;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface RateDAO extends CrudDAO<Rate> {
    List<Rate> getAll() throws DAOException;
    int getIdByName (String name) throws DAOException;
}
