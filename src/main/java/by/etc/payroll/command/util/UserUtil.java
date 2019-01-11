package by.etc.payroll.command.util;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.util.Roles;

public class UserUtil {
    public static boolean isUser (User user) throws ServiceUnauthorizedAccessException {
        if (user == null || !user.getRole().equalsIgnoreCase(Roles.USER)) {
            throw new ServiceUnauthorizedAccessException("Incorrect access");
        }
        return true;
    }

    public static boolean isAccountOfUser(User user, BankAccount bankAccount) throws ServiceUnauthorizedAccessException {
        isUser(user);
        if (user.getId() != bankAccount.getUserId()) {
            throw new ServiceUnauthorizedAccessException("Incorrect access");
        }
        return true;
    }
}
