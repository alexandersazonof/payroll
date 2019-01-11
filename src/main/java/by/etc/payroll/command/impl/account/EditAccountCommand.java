package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.service.exception.ServiceWrongNumberException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAccountCommand implements ActionCommand {

    private Logger LOG = LogManager.getLogger(EditAccountCommand.class);

    private static final String REDIRECT_PAGE = "/controller?command=editcardpage&";


    private static final String NUMBER_REQUEST_ATTRIBUTE = "Account";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        try {


            String accountNumber = request.getParameter(Attributes.FIELD_NUMBER);
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            BankAccount bankAccount = bankAccountService.getCardByNumber(accountNumber);


            request.getSession().setAttribute(NUMBER_REQUEST_ATTRIBUTE, bankAccount);
            response.sendRedirect(REDIRECT_PAGE);

        } catch (ServiceWrongNumberException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

    }

}
