package by.etc.payroll.command.impl.admin;

import by.etc.payroll.bean.Rate;
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
import org.w3c.dom.Attr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditRatePageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(EditRatePageCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User admin = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractAdminService adminService = serviceFactory.getAdminService();

        try {
            UserUtil.isAdmin(admin);
            String name = request.getParameter(Attributes.REQUEST_RATE_NAME);
            String description = request.getParameter(Attributes.REQUEST_RATE_DESCRIPTION);

            Rate rate = adminService.checkRate(name, description);

            request.setAttribute(Attributes.REQUEST_RATE, rate);
            request.getRequestDispatcher(Pages.JSP_ADMIN_EDIT_RATE).forward(request, response);


        } catch (ServiceQueryException e) {
          LOG.error(Message.INCORRECT_QUERY, e);
          response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
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
