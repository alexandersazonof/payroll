package by.etc.payroll.command.impl.transaction;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.AbstractBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TransferMoneyCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(TransferMoneyCommand.class);
    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=loginpage&";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/transfer.jsp";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


        try {

            User user = (User) request.getSession().getAttribute("user");
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            List<BankAccount> list = bankAccountService.getCardsByUserID(user);


            request.setAttribute(Attributes.FIELD_LIST_BANK_ACCOUNT, list);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR);

        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

    }
}
