package by.etc.payroll.dao;

import by.etc.payroll.bean.Transaction;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface TransactionDAO<Transaction> extends CrudDAO<Transaction> {
    List<Transaction> getAllByUserId(int userId) throws DAOException;

}
