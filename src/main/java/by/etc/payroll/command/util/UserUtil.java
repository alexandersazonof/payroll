package by.etc.payroll.command.util;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.util.Roles;

public class UserUtil {
    public static boolean isUser (User user) throws ServiceUnauthorizedAccessException {
        if (user == null ) {
            throw new ServiceUnauthorizedAccessException(Message.INCORRECT_ACCESS);
        }
        return true;
    }

    public static boolean isAccountOfUser(User user, BankAccount bankAccount) throws ServiceUnauthorizedAccessException {
        isUser(user);
        if (user.getId() != bankAccount.getUserId()) {
            if (!user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
                throw new ServiceUnauthorizedAccessException(Message.INCORRECT_ACCESS);
            }
        }
        return true;
    }

    public static boolean isAdmin (User user) throws ServiceUnauthorizedAccessException{
        if (user == null || !user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
            throw new ServiceUnauthorizedAccessException(Message.INCORRECT_ACCESS);
        }
        return true;
    }

    public static boolean isOnlyUser (User user, BankAccount bankAccount) throws ServiceUnauthorizedAccessException{
        if (user == null || user.getId() != bankAccount.getUserId()) {
            throw new ServiceUnauthorizedAccessException(Message.INCORRECT_ACCESS);
        }
        return true;
    }
}
