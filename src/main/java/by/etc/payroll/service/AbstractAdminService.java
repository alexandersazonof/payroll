package by.etc.payroll.service;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.service.exception.ServiceException;

import java.util.List;

public interface AbstractAdminService {
    List<User> getAllUserWithoutPassword() throws ServiceException;
    List<BankAccount> getAllBankAccount() throws ServiceException;

}
