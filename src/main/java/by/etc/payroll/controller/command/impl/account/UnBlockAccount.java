package by.etc.payroll.controller.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String bankAccountNumber = request.getParameter(Attributes.REQUEST_ACCOUNT_NUMBER);

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
                response.sendRedirect(String.format(Pages.REDIRECT_PAGE_AFTER_SUCCESS_UNBLOCKACCOUNT, bankAccountNumber));
            }
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceQueryException e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
