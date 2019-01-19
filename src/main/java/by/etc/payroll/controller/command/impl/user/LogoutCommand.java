package by.etc.payroll.controller.command.impl.user;

import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements ActionCommand {
    private static final String REDIRECT_PAGE = "/controller?command=mainpage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);


        if (session != null) {
            session.removeAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        }

        try {
            response.sendRedirect(REDIRECT_PAGE);
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
