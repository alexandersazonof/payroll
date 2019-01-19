package by.etc.payroll.controller.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.Attributes;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.Pages;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractAdminService;
import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnBlockAccount implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(UnBlockAccount.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";
    private static final String REDIRECT_PAGE_INCORRECT_QUERY = "/controller?command=mainPage&wrongquery=true";
    private static final String REDIRECT_PAGE_AFTER_SUCCESS = "/controller?command=showaccount&number=%s&sucunblock=true";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute("user");
        String bankAccountNumber = request.getParameter("accountNumber");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractBankAccountService bankAccountService = serviceFactory.getBankAccountService();
        AbstractAdminService adminService = serviceFactory.getAdminService();
        try {
            if (user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
                adminService.unBlockAccount(bankAccountNumber);


                int applicationSize = adminService.getAllApplication().size();
                request.getSession().setAttribute(Attributes.SESSION_FIELD_APPLICATION_SIZE, applicationSize);

                response.sendRedirect(Pages.REDIRECT_ADMIN_UNBLOCK_ACCOUNT);
            } else {
                bankAccountService.unBlockAccount(bankAccountNumber, user);
                response.sendRedirect(String.format(REDIRECT_PAGE_AFTER_SUCCESS, bankAccountNumber));
            }
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error("Incorrect access", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceQueryException e) {
            LOG.error("Incorrect query", e);
            response.sendRedirect(REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
