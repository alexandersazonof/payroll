package by.etc.payroll.dao;

import by.etc.payroll.dao.exception.DAOException;

public interface CrudDAO<T> {
    boolean insert (T t) throws DAOException;

    T find (int id) throws DAOException;

    boolean update (T t) throws DAOException;

    boolean delete (T t) throws DAOException;
}
