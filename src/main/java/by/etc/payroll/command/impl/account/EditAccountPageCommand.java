package by.etc.payroll.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAccountPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(EditAccountPageCommand.class);

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?commad=loginpage";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/edit_account.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {

            if (!Validator.validateUser((User) request.getSession().getAttribute("user"))) {
                throw new ServiceUnauthorizedAccessException("Incorrect access");
            }

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);



        } catch (ServiceUnauthorizedAccessException e) {

            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);

        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
