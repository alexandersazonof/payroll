package by.etc.payroll.command.impl.general;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewMainPageCommand implements ActionCommand {

    private Logger LOG = LogManager.getLogger(ViewMainPageCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String USER_PAGE_PATH = "/controller?command=usermainpage";
    private static final String JSP_PAGE_PATH = "index.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            User user = (User) request.getSession().getAttribute("user");

            if (user == null || !user.getRole().equalsIgnoreCase(Roles.USER)) {
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } else {
                request.getRequestDispatcher(USER_PAGE_PATH).forward(request, response);
            }
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
