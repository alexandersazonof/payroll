package by.etc.payroll.command.impl.admin;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractAdminService;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteRateCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(DeleteRateCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String name = request.getParameter(Attributes.REQUEST_RATE_NAME);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractAdminService adminService = serviceFactory.getAdminService();


        try {
            UserUtil.isAdmin(user);
            adminService.deleteRate(name);

            response.sendRedirect(Pages.REDIRECT_ADMIN_AFTER_SUCCESS_DELETE);
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
