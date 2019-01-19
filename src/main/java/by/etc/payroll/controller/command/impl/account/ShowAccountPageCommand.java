package by.etc.payroll.controller.command.impl.account;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Operation;
import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.service.AbstractCardService;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
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


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        try {

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


            User user = (User) request.getSession().getAttribute(Attributes.FIELD_USER);
            String bankAccountNumber = request.getParameter(Attributes.REQUEST_NUMBER);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();



            BankAccount bankAccount = bankAccountService.getCardByNumber(bankAccountNumber);
            UserUtil.isAccountOfUser(user, bankAccount);

            int totalMoney = bankAccount.getCountOfMoney();

            for (Card card :bankAccount.getCardList()) {
                totalMoney += card.getMoney();
            }

            String searchKey = request.getParameter(Attributes.REQUEST_SEARCH);

            List<Operation> operationList = bankAccountService.searchByWord(bankAccountNumber, searchKey);

            request.setAttribute(Attributes.REQUEST_TOTAL_MONEY, totalMoney);
            request.setAttribute(Attributes.REQUEST_OPERATION_LIST, operationList);
            request.setAttribute(Attributes.REQUEST_BANK_ACCOUNT, bankAccount);


            request.getRequestDispatcher(Pages.JSP_SHOW_ACCOUNT_PAGE).forward(request, response);
        } catch (ServiceQueryException e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }

    }
}
