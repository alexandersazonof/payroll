package by.etc.payroll.dao;

import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface UserDAO<User> extends CrudDAO<User> {

    List<User> getAllUserWithoutPassword() throws DAOException;

    User findByLogin (String login) throws DAOException;
    User findByEmail (String email) throws DAOException;

    User findByLoginNotConsideringId (String login, int id) throws DAOException;
    User findByEmailNotConsideringId (String email, int id) throws DAOException;
}
