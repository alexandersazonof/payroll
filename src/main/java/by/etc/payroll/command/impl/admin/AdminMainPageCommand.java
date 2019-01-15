package by.etc.payroll.command.impl.admin;

import by.etc.payroll.bean.Application;
import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.bean.UserWithBankAccount;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractAdminService;
import by.etc.payroll.service.creator.Creator;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminMainPageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(AdminMainPageCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        AbstractAdminService abstractAdminService = ServiceFactory.getInstance().getAdminService();

        try {
            UserUtil.isAdmin(user);

            int applicationSize = abstractAdminService.getAllApplication().size();
            request.getSession().setAttribute(Attributes.SESSION_FIELD_APPLICATION_SIZE, applicationSize);

            List<BankAccount> bankAccountListList = abstractAdminService.getAllBankAccount();
            List<User> userList = abstractAdminService.getAllUserWithoutPassword();

            List<UserWithBankAccount> userWithBankAccountList = Creator.takeUserWithBankAccount(userList, bankAccountListList);

            request.setAttribute(Attributes.REQUEST_USER_WITH_BANK_ACCOUNT_LIST, userWithBankAccountList);
            request.getRequestDispatcher(Pages.JSP_ADMIN_MAIN_PAGE).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
