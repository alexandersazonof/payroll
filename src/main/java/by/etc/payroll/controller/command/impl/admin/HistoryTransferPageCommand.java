package by.etc.payroll.controller.command.impl.admin;

import by.etc.payroll.bean.Operation;
import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractAdminService;
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

public class HistoryTransferPageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(HistoryTransferPageCommand.class);
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
            List<Operation> operationList = adminService.getAllOperation();

            request.setAttribute(Attributes.REQUEST_OPERATION_LIST, operationList);
            request.getRequestDispatcher(Pages.JSP_ADMIN_OPERATION_PAGE).forward(request, response);
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
