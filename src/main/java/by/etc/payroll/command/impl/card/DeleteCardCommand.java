package by.etc.payroll.command.impl.card;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.Operation;
import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.command.util.UserUtil;
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
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private final static String ACTION = "Delete card : ";

    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";
    private static final String REDIRECT_PAGE_INCORRECT_QUERY = "/controller?command=mainPage&wrongquery=true";
    private static final String REDIRECT_AFTER_SUCCESS = "/controller?command=mainPage&scdrop=true";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String cardId = request.getParameter("cid");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ConcreteCardService concreteCardService = serviceFactory.getCardService();
        ConcreteBankAccountService concreteBankAccountService = serviceFactory.getBankAccountService();
        User user = (User)request.getSession().getAttribute("user");

        try {
            Card card = concreteCardService.getCard(cardId);
            BankAccount bankAccount = concreteBankAccountService.getAccountById(card.getIdAccount());
            Operation operation = Creator.takeOperation(ACTION + card.getNumber(), bankAccount.getNumber(), user.getId());
            UserUtil.isAccountOfUser(user, bankAccount);

            concreteCardService.deleteCard(card, bankAccount, operation);

            response.sendRedirect(REDIRECT_AFTER_SUCCESS);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error("Incorrect access", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceWrongCardNumber e) {
            LOG.error("Incorrect query", e);
            response.sendRedirect(REDIRECT_PAGE_INCORRECT_QUERY);
        }
        catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

    }
}
