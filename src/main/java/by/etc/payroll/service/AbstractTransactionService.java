package by.etc.payroll.service;

import by.etc.payroll.bean.Transaction;
import by.etc.payroll.bean.User;
import by.etc.payroll.service.exception.ServiceException;

import java.util.List;

public interface AbstractTransactionService {
    boolean insertTransaction (String from, String to, String count) throws ServiceException;
    List<Transaction> showHistory (User user) throws ServiceException;
}
