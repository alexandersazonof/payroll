package by.etc.payroll.command.util;

public interface Pages {
    String REDIRECT_PAGE_AFTER_INCORRECT_ACCESS = "/controller?command=mainPage&useraccess=true";
    String REDIRECT_PAGE_INCORRECT_QUERY = "/controller?command=mainPage&wrongquery=true";


    String REDIRECT_DELETE_ACCOUNT_INCORRECT_MONEY = "/controller?command=showaccount&number=%s&incmoney=true";
    String REDIRECT_DELETE_ACCOUNT_SUCCESS = "/controller?command=mainPage&successdelete=true";
}
