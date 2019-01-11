package by.etc.payroll.command.impl.user;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.impl.transaction.HistoryTransactionPageCommand;
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

public class EditUserPageCommand implements ActionCommand {
    Logger LOG = LogManager.getLogger(EditUserPageCommand.class);

    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=loginpage&";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/edit_user.jsp";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                throw new CommandException();
            }
            if (!user.getRole().equalsIgnoreCase(Roles.USER)) {
                LOG.error("Incorrect access");
                response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR);
            }

            request.setAttribute("user", user);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

    }
}
