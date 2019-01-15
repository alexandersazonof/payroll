package by.etc.payroll.command.util;

public interface Pages {
    String REDIRECT_PAGE_AFTER_INCORRECT_ACCESS = "/controller?command=mainPage&useraccess=true";
    String REDIRECT_PAGE_INCORRECT_QUERY = "/controller?command=mainPage&wrongquery=true";
    String REDIRECT_PAGE_SUCCESS_TRANSFER_ACCOUNT = "/controller?command=mainPage&sucmonacc=true";

    String JSP_TRANSFER_ACCOUNT_MONEY = "WEB-INF/jsp/home/transfer_account_money.jsp";
    String JSP_ADMIN_MAIN_PAGE = "WEB-INF/jsp/admin/admin_panel.jsp";
    String JSP_ADMIN_OPERATION_PAGE = "WEB-INF/jsp/admin/operation_history.jsp";

    String JSP_ADMIN_APPLICATIONS = "WEB-INF/jsp/admin/application.jsp";
    String JSP_ADMIN_RATES = "WEB-INF/jsp/admin/rates.jsp";
    String REDIRACT_ADMIN_RATES = "/controller?command=RATEPAGE";

    String JSP_ADMIN_NEW_RATE = "WEB-INF/jsp/admin/new_rate.jsp";
    String REDIRECT_ADMIN_NEW_RATE_INCORRECT_VALUE = "/controller?command=newratepage&incorrectvaluerate=true";
    String REDIRACT_ADMIN_AFTER_SUCCESS_ADD = "/controller?command=RATEPAGE&successaddrate=ture";

    String REDIRECT_ADMIN_VALUTE_AFTER_SUCCESS_EDIT = "/controller?command=VALUTEPAGE&successeditvalute=true";

    String REDIRECT_ADMIN_UNBLOCK_ACCOUNT = "/controller?command=mainPage&unblockaccount=true";
    String JSP_ADMIN_EDIT_VALUTE = "WEB-INF/jsp/admin/edit_valute.jsp";

    String JSP_ADMIN_EDIT_RATE = "WEB-INF/jsp/admin/edit_rate.jsp";
    String REDIRECT_ADMIN_PAGE_EDIT_INCORRECT_VALUE = "/controller?command=EDITRATEPAGE&incnamerate=true";
    String REDIRECT_ADMIN_AFTER_SUCCESS_EDIT = "/controller?command=RATEPAGE&successeditrate=true";

    String JSP_ADMIN_VALUTE_PAGE = "WEB-INF/jsp/admin/valute.jsp";

    String REDIRECT_ADMIN_AFTER_SUCCESS_DELETE = "/controller?command=RATEPAGE&successdeleterate=true";

    String REDIRECT_ADMIN_PAGE_COMMAND = "/controller?command=ADMINPAGE";

    String REDIRECT_TRANSFER_ACCOUNT_WRONG_COUNT = "/controller?command=TRANSFERACCOUNTMONEY&accountNumber=%s&wrongcount=true";

    String REDIRECT_DELETE_ACCOUNT_INCORRECT_MONEY = "/controller?command=showaccount&number=%s&incmoney=true";
    String REDIRECT_DELETE_ACCOUNT_SUCCESS = "/controller?command=mainPage&successdelete=true";
}
