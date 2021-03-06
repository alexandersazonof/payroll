package by.etc.payroll.controller.command.impl.admin;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractAdminService;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongNameException;
import by.etc.payroll.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddRateCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(AddRateCommand.class);



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String name = request.getParameter(Attributes.REQUEST_RATE_NAME);
        String description = request.getParameter(Attributes.REQUEST_RATE_DESCRIPTION);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractAdminService adminService = serviceFactory.getAdminService();

        try {
            UserUtil.isAdmin(user);

            adminService.addRate(name, description);
            response.sendRedirect(Pages.REDIRACT_ADMIN_AFTER_SUCCESS_ADD);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceWrongNameException e) {
            LOG.error(Message.INCORRECT_VALUE, e);
            response.sendRedirect(Pages.REDIRECT_ADMIN_NEW_RATE_INCORRECT_VALUE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
