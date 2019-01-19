package by.etc.payroll.controller.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.Message;
import by.etc.payroll.controller.command.util.Pages;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongNameException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.controller.command.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewAccountCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(NewAccountCommand.class);


    private static final String WRONG_NAME_REQUEST_ATTR = "wrongName";
    private static final String NAME_REQUEST_ATTR = "name";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        String name = request.getParameter(Attributes.FIELD_NAME);
        String valute = request.getParameter(Attributes.REQUEST_VALUTE);

        try {

            User user = (User)request.getSession().getAttribute(Attributes.FIELD_USER);


            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            bankAccountService.addCard(name, valute, user);
            response.sendRedirect(Pages.REDIRECT_PAGE_SUCCESS_CREATE_NEW_ACCOUNT);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceWrongNameException e) {
            LOG.error(Message.INCORRECT_VALUE, e);
            response.sendRedirect(makeErrorRedirectString(WRONG_NAME_REQUEST_ATTR, name));
        } catch(ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

    }

    private String makeErrorRedirectString(String errorName, String name) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(Pages.REDIRECT_PAGE_AFTER_UNSUCCESS_CREATE_NEW_ACCOUNT);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(NAME_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(name);

        return parameters.toString();

    }
}
