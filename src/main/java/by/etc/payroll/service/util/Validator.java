package by.etc.payroll.service.util;



import by.etc.payroll.bean.User;
import by.etc.payroll.util.Roles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String LOGIN_REGEXP = "^[a-zA-Z]{4,39}$";
    private static final String PASSWORD_REGEXP = "(?=^.{6,45}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final String NAME_REGEXP = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]{2,40}$";
    private static final String NUMBER_REGEXP = "^[B][Y][0-9].{5,15}$";

    public static boolean validateString (String value) {
        return !(value == null || value.isEmpty());
    }

    public static boolean validateLogin (String value) {
        if (!validateString(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(LOGIN_REGEXP);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean validateNumber (String value) {
        if (!validateString(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(NUMBER_REGEXP);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean validateUser (User user) {
        return !(user == null || !user.getRole().equalsIgnoreCase(Roles.USER));
    }

    public static boolean validatePassword (String value) {
        if (!validateString(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();

    }

    public static boolean validateEmail (String value) {
        if (!validateString(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEXP);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean validateName (String value) {
        if (!validateString(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(NAME_REGEXP);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
