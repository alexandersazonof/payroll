package by.etc.payroll.service;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AbstractUserService {

    void registration (User user) throws ServiceException;
    User logIn (String login, String password) throws ServiceException;
    User save (int id, String login, String email, String firstName, String lastName, String newPassword, String confirmPassword, String oldPassword) throws ServiceException;

    List<BankAccount> getListBankAccountByUser (User user) throws ServiceException;
}
