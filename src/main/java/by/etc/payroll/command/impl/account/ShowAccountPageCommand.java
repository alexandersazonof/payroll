package by.etc.payroll.command.impl.account;

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

public class ShowAccountPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(ShowAccountPageCommand.class);
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/show_account.jsp";
    private static final String REDIRECT_PAGE_ERROR_ACCESS = "/controller?command=loginpage&";

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String LIST_CARDS_REQUEST_ATTRIBUTE = "listAccount";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        try {

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);



            User user = (User) request.getSession().getAttribute(Attributes.FIELD_USER);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            List<BankAccount> listBankAccount = bankAccountService.getCardsByUserID(user);


            request.setAttribute(LIST_CARDS_REQUEST_ATTRIBUTE, listBankAccount);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {

            LOG.error(e.getMessage(), e);

            response.sendRedirect(REDIRECT_PAGE_ERROR_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

    }
}
