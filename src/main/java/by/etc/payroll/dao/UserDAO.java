package by.etc.payroll.dao;

import by.etc.payroll.dao.exception.DAOException;

public interface UserDAO<User> extends CrudDAO<User> {

    User findByLogin (String login) throws DAOException;
    User findByEmail (String email) throws DAOException;

    User findByLoginNotConsideringId (String login, int id) throws DAOException;
    User findByEmailNotConsideringId (String email, int id) throws DAOException;
}
