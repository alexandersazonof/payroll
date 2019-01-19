package by.etc.payroll.dao;

import by.etc.payroll.bean.UserData;
import by.etc.payroll.dao.exception.DAOException;

public interface UserDataDAO extends CrudDAO<UserData> {
    UserData findByLastName (String lastName) throws DAOException;
    boolean deleteByLastName (String lastName) throws DAOException;
}
