package by.etc.payroll.command.impl.transfer;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
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

public class SendAccountMoneyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(SendAccountMoneyCommand.class);

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String accountNumber = request.getParameter(Attributes.REQUEST_ACCOUNT_NUMBER);
        String cardNumber = request.getParameter(Attributes.REQUEST_CARD_NUMBER);
        String money = request.getParameter(Attributes.REQUEST_MONEY);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();

        try {
            bankAccountService.sendMoney(user, accountNumber, cardNumber, money);

            response.sendRedirect(Pages.REDIRECT_PAGE_SUCCESS_TRANSFER_ACCOUNT);
        } catch (ServiceWrongCountException e) {
            LOG.error(Message.INCORRECT_MONEY, e);
            response.sendRedirect(String.format(Pages.REDIRECT_TRANSFER_ACCOUNT_WRONG_COUNT, accountNumber));
        } catch (ServiceQueryException e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
