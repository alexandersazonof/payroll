package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongCountException;
import by.etc.payroll.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAccountCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(DeleteAccountCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String accountNumber = request.getParameter(Attributes.REQUEST_ACCOUNT_NUMBER);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();

        try {
            BankAccount bankAccount = bankAccountService.getCardByNumber(accountNumber);

            bankAccountService.deleteBankAccount(bankAccount, user);

            response.sendRedirect(Pages.REDIRECT_DELETE_ACCOUNT_SUCCESS);
        } catch (ServiceWrongCountException e) {
            LOG.error(Message.INCORRECT_MONEY, e);
            response.sendRedirect(String.format(Pages.REDIRECT_DELETE_ACCOUNT_INCORRECT_MONEY, accountNumber));
        } catch (ServiceQueryException e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
