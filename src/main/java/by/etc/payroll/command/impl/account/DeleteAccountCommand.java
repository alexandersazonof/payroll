package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongNumberException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.util.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAccountCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(DeleteAccountCommand.class);


    private static final String REDIRECT_PAGE = "/controller?command=showcard&";
    private static final String REDIRECT_PAGE_ERROR_ACCESS = "/controller?command=mainpage&";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        try {

            String accountNumber = request.getParameter(Attributes.FIELD_NUMBER);
            User user = (User) request.getSession().getAttribute("user");

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();


            bankAccountService.deleteCard(accountNumber, user);
            response.sendRedirect(REDIRECT_PAGE);

        } catch (ServiceWrongNumberException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE);
        }  catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_ERROR_ACCESS);
        }  catch (ServiceException e) {

            throw new CommandException(e.getMessage(), e);
        }

    }

}
