package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Operation;
import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.command.util.UserUtil;
import by.etc.payroll.service.AbstractCardService;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.command.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAccountPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(ShowAccountPageCommand.class);
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/show_account.jsp";
    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";
    private static final String REDIRECT_PAGE_INCORRECT_QUERY = "/controller?command=mainPage&wrongquery=true";


    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String LIST_CARDS_REQUEST_ATTRIBUTE = "listAccount";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        try {

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


            User user = (User) request.getSession().getAttribute(Attributes.FIELD_USER);
            String bankAccountNumber = request.getParameter("number");

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();
            AbstractCardService cardService = serviceFactory.getCardService();


            BankAccount bankAccount = bankAccountService.getCardByNumber(bankAccountNumber);
            UserUtil.isAccountOfUser(user, bankAccount);

            int totalMoney = bankAccount.getCountOfMoney();

            for (Card card :bankAccount.getCardList()) {
                totalMoney += card.getMoney();
            }

            String searchKey = request.getParameter(Attributes.REQUEST_SEARCH);

            List<Operation> operationList = bankAccountService.searchByWord(bankAccountNumber, searchKey);

            request.setAttribute("totalMoney", totalMoney);
            request.setAttribute("operationList", operationList);
            request.setAttribute("bankAccount", bankAccount);


            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceQueryException e) {
            LOG.error("Incorrect query");
            response.sendRedirect(REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

    }
}
