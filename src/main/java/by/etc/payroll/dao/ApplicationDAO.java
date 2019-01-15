package by.etc.payroll.dao;

import by.etc.payroll.bean.Application;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface ApplicationDAO extends CrudDAO<Application> {
    boolean deleteByNumberId (int id) throws DAOException;
    List<Application> getAll () throws DAOException;
}
