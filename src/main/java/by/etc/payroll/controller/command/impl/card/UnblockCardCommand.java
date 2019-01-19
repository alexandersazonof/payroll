package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceBlockAccountException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import by.etc.payroll.service.impl.ConcreteCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockCardCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(UnblockCardCommand.class);


    private static final String ACTION_BLOCK = "Unblock card : ";

    private static final String REDIRECT_PAGE_AFTER_SUCCESS = "/controller?command=showcardpage&cid=%d&unblocksuccess=true";
    private static final String REDIRECT_PAGE_INCORRECT_ACCOUNT_BLOCK = "/controller?command=showcardpage&cid=%d&blockaccount=true";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
        String cid  = request.getParameter(Attributes.REQUSET_CARD_ID);

        int cardId = 0;

        try {

            cardId = Integer.valueOf(cid);

            User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
            String bankAccountNumber = request.getParameter(Attributes.REQUEST_ACCOUNT);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();
            ConcreteCardService concreteCardService = serviceFactory.getCardService();

            BankAccount bankAccount = bankAccountService.getCardByNumber(bankAccountNumber);

            if (!bankAccount.isStatus()) {
                throw new ServiceBlockAccountException();
            }
            UserUtil.isAccountOfUser(user, bankAccount);

            String cardNumber = request.getParameter(Attributes.REQUEST_CARD);
            Card card = concreteCardService.getCardByNumber(cardNumber);
            concreteCardService.unBlockCard(card);
            concreteCardService.doOperation(ACTION_BLOCK + cardNumber , bankAccountNumber, user.getId());

            response.sendRedirect(String.format(REDIRECT_PAGE_AFTER_SUCCESS, cardId));


        } catch (NumberFormatException e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceBlockAccountException e) {
            LOG.error(Message.ALERT_ACCOUNT_IS_BLOCK, e);
            response.sendRedirect(String.format(REDIRECT_PAGE_INCORRECT_ACCOUNT_BLOCK, cardId));
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
