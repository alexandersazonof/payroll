package by.etc.payroll.command.impl.card;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.command.util.UserUtil;
import by.etc.payroll.controller.exception.CommandException;
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

public class BlockCardCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(BlockCardCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String ACTION_BLOCK = "Block card : ";

    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";
    private static final String REDIRECT_PAGE_INCORRECT_QUERY = "/controller?command=mainPage&wrongquery=true";
    private static final String REDIRECT_PAGE_AFTER_SUCCESS = "/controller?command=showcardpage&cid=%d&blocksuccess=true";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            User user = (User)request.getSession().getAttribute("user");
            String bankAccountNumber = request.getParameter("account");

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();
            ConcreteCardService concreteCardService = serviceFactory.getCardService();

            BankAccount bankAccount = bankAccountService.getCardByNumber(bankAccountNumber);
            UserUtil.isAccountOfUser(user, bankAccount);

            String cardNumber = request.getParameter("card");
            Card card = concreteCardService.getCardByNumber(cardNumber);
            concreteCardService.blockCard(card);
            concreteCardService.doOperation(ACTION_BLOCK + cardNumber , bankAccountNumber, user.getId());

            response.sendRedirect(String.format(REDIRECT_PAGE_AFTER_SUCCESS, card.getId()));
        } catch (ServiceQueryException e) {
            LOG.error("Incorrect query", e);
            response.sendRedirect(REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error("Incorrect access", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

    }
}
