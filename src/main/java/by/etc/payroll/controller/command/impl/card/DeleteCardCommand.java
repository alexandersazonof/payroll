package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Operation;
import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongCardNumber;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import by.etc.payroll.service.impl.ConcreteCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCardCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(DeleteCardCommand.class);

    private final static String ACTION = "Delete card : ";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String cardId = request.getParameter(Attributes.REQUSET_CARD_ID);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ConcreteCardService concreteCardService = serviceFactory.getCardService();
        ConcreteBankAccountService concreteBankAccountService = serviceFactory.getBankAccountService();
        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);

        try {
            Card card = concreteCardService.getCard(cardId);
            BankAccount bankAccount = concreteBankAccountService.getAccountById(card.getIdAccount());
            Operation operation = Creator.takeOperation(ACTION + card.getNumber(), bankAccount.getNumber(), user.getId());
            UserUtil.isAccountOfUser(user, bankAccount);

            concreteCardService.deleteCard(card, bankAccount, operation);

            response.sendRedirect(Pages.REDIRECT_PAGE_SUCCESS_DELETE_CARD);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceWrongCardNumber e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        }
        catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

    }
}
