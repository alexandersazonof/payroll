package by.etc.payroll.service.util;

import by.etc.payroll.bean.Operation;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.SqlOperationDAO;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.ServiceException;

public class EventLog {
    public static void write (String action, String number, int id) throws ServiceException {


        Operation operation = Creator.takeOperation(action, number, id);
        try {

            DaoFactory daoFactory = DaoFactory.getInstance();
            SqlOperationDAO operationDAO = daoFactory.getOperationDAO();

            operationDAO.insert(operation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }
}
