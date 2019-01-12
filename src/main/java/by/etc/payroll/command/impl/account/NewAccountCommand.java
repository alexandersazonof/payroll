package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongNameException;
import by.etc.payroll.service.exception.ServiceWrongNumberException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteCardService;
import by.etc.payroll.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NewAccountCommand implements ActionCommand {
    private AbstractBankAccountService service = new ConcreteBankAccountService();
    private Logger LOG = LogManager.getLogger(NewAccountCommand.class);

    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?commad=mainpage&useraccess=true";
    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=newaccountpage&";
    private static final String REDIRECT_PAGE_AFTER_SUCCESS = "/controller?command=mainpage&msg=success";

    private static final String WRONG_NAME_REQUEST_ATTR = "wrongName";
    private static final String NAME_REQUEST_ATTR = "name";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        String name = request.getParameter(Attributes.FIELD_NAME);
        String valute = request.getParameter("valute");

        try {

            User user = (User)request.getSession().getAttribute(Attributes.FIELD_USER);


            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            bankAccountService.addCard(name, valute, user);
            response.sendRedirect(REDIRECT_PAGE_AFTER_SUCCESS);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceWrongNameException e) {
            LOG.error(e.getMessage(), e);
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
        parameters.append(REDIRECT_PAGE_AFTER_ERROR);
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
