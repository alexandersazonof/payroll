package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.util.Validator;
import by.etc.payroll.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewAccountPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(NewAccountPageCommand.class);
    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?commad=loginpage";

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/new_account.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {

            User user = (User) request.getSession().getAttribute("user");

            if (!Validator.validateUser(user)) {
                throw new ServiceUnauthorizedAccessException("Incorrect user");
            }

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
