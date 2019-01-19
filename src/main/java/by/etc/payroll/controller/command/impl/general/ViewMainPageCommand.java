package by.etc.payroll.controller.command.impl.general;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.Attributes;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.Pages;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewMainPageCommand implements ActionCommand {

    private Logger LOG = LogManager.getLogger(ViewMainPageCommand.class);

    private static final String USER_PAGE_PATH = "/controller?command=usermainpage";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/general/main.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            User user = (User) request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);

            if (user == null) {
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } else if (user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
                request.getRequestDispatcher(Pages.REDIRECT_ADMIN_PAGE_COMMAND).forward(request, response);
            } else {
                request.getRequestDispatcher(USER_PAGE_PATH).forward(request, response);
            }
        } catch (ServletException e) {
            throw new CommandException(e);
        }
    }
}
