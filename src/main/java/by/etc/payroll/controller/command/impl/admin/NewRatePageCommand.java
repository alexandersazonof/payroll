package by.etc.payroll.controller.command.impl.admin;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewRatePageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(NewRatePageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);

        try {
            UserUtil.isAdmin(user);

            request.getRequestDispatcher(Pages.JSP_ADMIN_NEW_RATE).forward(request, response);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }


    }
}
