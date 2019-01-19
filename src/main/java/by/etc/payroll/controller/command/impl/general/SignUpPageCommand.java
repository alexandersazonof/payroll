package by.etc.payroll.controller.command.impl.general;

import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.Attributes;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpPageCommand implements ActionCommand {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/signup.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request,response);

        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
