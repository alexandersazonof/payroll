package by.etc.payroll.command.impl.transaction;

import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.service.exception.ServiceBlockAccountException;
import by.etc.payroll.service.exception.ServiceWrongCountException;
import by.etc.payroll.service.exception.ServiceWrongNumberException;
import by.etc.payroll.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractTransactionService;
import by.etc.payroll.service.impl.ConcreteTransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendMoneyCommand implements ActionCommand {
    private AbstractTransactionService service = new ConcreteTransactionService();
    private Logger LOG = LogManager.getLogger(SendMoneyCommand.class);

    private static final String REDIRECT_PAGE_AFTER_ACCESS = "/controller?command=mainpage&";
    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=transfer&";

    private static final String WRONG_NUMBER_REQUEST_ATTR = "wrongNumber";
    private static final String WRONG_COUNT_REQUEST_ATTR = "wrongCount";
    private static final String WRONG_BLOCK_REQUEST_ATTR = "wrongBlock";

    private static final String FROM_NUMBER_REQUEST_ATTR = "FromNumber";
    private static final String TO_NUMBER_REQUEST_ATTR = "ToNumber";
    private static final String COUNT_REQUEST_ATTR = "Count";

    private static final String AMP = "&";
    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        String fromNumber = request.getParameter(Attributes.FIELD_FROM_ACCOUNT);
        String toNumber = request.getParameter(Attributes.FIELD_TO_ACCOUNT);
        String stringCount = request.getParameter(Attributes.FIELD_COUNT);

        try {




            service.insertTransaction(fromNumber, toNumber, stringCount);

            response.sendRedirect(REDIRECT_PAGE_AFTER_ACCESS);
        }  catch (ServiceBlockAccountException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(makeErrorRedirectString(WRONG_BLOCK_REQUEST_ATTR,fromNumber, toNumber, stringCount ));
        }  catch (ServiceWrongNumberException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(makeErrorRedirectString(WRONG_NUMBER_REQUEST_ATTR,fromNumber, toNumber, stringCount ));
        }  catch (ServiceWrongCountException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(makeErrorRedirectString(WRONG_COUNT_REQUEST_ATTR,fromNumber, toNumber, stringCount ));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    private String makeErrorRedirectString(String errorName, String fromNumber, String toNumber, String count) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_PAGE_AFTER_ERROR);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(FROM_NUMBER_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(fromNumber);
        parameters.append(AMP);
        parameters.append(TO_NUMBER_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(toNumber);
        parameters.append(AMP);
        parameters.append(COUNT_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(count);

        return parameters.toString();

    }
}
