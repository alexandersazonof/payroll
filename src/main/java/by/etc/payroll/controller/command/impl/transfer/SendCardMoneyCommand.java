package by.etc.payroll.controller.command.impl.transfer;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendCardMoneyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(SendCardMoneyCommand.class);

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";
    private static final String REDIRECT_AFTER_SUCCESS = "/controller?command=mainPage&sctran=true";
    private static final String REDIRECT_WRONG_VALUE = "/controller?command=transfermoney&";

    private static final String WRONG_PASSWORD = "wrongPassword";
    private static final String WRONG_NUMBER = "wrongNumber";
    private static final String WRONG_COUNT = "wrongCount";
    private static final String WRONG_BLOCK = "wrongBlock";

    private static final String TO_CARD_NUMBER_REQUSET_ATTR = "toCard";
    private static final String COUNT_REQUSET_ATTR = "count";

    private static final String AMP = "&";
    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute("user");
        String fromCard = request.getParameter("fromCardNumber");
        String toCard = request.getParameter("toCardNumber");
        String count = request.getParameter("money");
        String password = request.getParameter("password");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ConcreteCardService concreteCardService = serviceFactory.getCardService();

        try {

            concreteCardService.sendMoney(user, fromCard, toCard, count, password);
            response.sendRedirect(REDIRECT_AFTER_SUCCESS);

        } catch (ServiceBlockAccountException e) {
            LOG.error("Block : " + e.getMessage(), e);
            response.sendRedirect(makeErrorRedirectStringForBlock(WRONG_BLOCK, e.getMessage(), toCard, count));
        } catch (ServiceWrongPasswordException e) {
            LOG.error("Incorrect password", e);
            response.sendRedirect(makeErrorRedirectString(WRONG_PASSWORD, toCard, count));
        } catch (ServiceWrongCardNumber e) {
            LOG.error("Incorrect number", e);
            response.sendRedirect(makeErrorRedirectString(WRONG_NUMBER, toCard, count));
        } catch (ServiceWrongCountException e) {
            LOG.error("Incorrect count", e);
            response.sendRedirect(makeErrorRedirectString(WRONG_COUNT, toCard, count));
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error("Incorrect access", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
    private String makeErrorRedirectString(String errorName, String toCard, String count) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_WRONG_VALUE);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(TO_CARD_NUMBER_REQUSET_ATTR);
        parameters.append(EQ);
        parameters.append(toCard);
        parameters.append(AMP);
        parameters.append(COUNT_REQUSET_ATTR);
        parameters.append(EQ);
        parameters.append(count);

        return parameters.toString();

    }

    private String makeErrorRedirectStringForBlock(String errorName, String blockNumber, String toCard, String count) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_WRONG_VALUE);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(blockNumber);
        parameters.append(AMP);
        parameters.append(TO_CARD_NUMBER_REQUSET_ATTR);
        parameters.append(EQ);
        parameters.append(toCard);
        parameters.append(AMP);
        parameters.append(COUNT_REQUSET_ATTR);
        parameters.append(EQ);
        parameters.append(count);

        return parameters.toString();

    }

}
