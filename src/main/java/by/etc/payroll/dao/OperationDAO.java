package by.etc.payroll.dao;

import by.etc.payroll.bean.Operation;
import by.etc.payroll.dao.exception.DAOException;

import java.util.List;

public interface OperationDAO extends CrudDAO<Operation> {
    List<Operation> getAllByNumber (String number) throws DAOException;
}
