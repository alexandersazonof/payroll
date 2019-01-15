package by.etc.payroll.service;

import by.etc.payroll.bean.*;
import by.etc.payroll.service.exception.ServiceException;

import java.util.List;

public interface AbstractAdminService {
    List<User> getAllUserWithoutPassword() throws ServiceException;
    List<BankAccount> getAllBankAccount() throws ServiceException;
    List<Transfer> getAllTransfer() throws ServiceException;
    List<Operation> getAllOperation() throws ServiceException;

    Rate checkRate (String name, String description) throws ServiceException;
    boolean updateRate (String name, String description) throws ServiceException;
    boolean addRate (String name, String description) throws ServiceException;
    boolean deleteRate (String name) throws ServiceException;
}
