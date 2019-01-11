package by.etc.payroll.dao;

import by.etc.payroll.bean.Company;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface CompanyDAO extends CrudDAO<Company> {
    List<Company> getAll() throws DAOException;
    int getIdByName (String name) throws DAOException;
}
