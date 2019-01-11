package by.etc.payroll.command.impl.user;

import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements ActionCommand {
    private static final String REDIRECT_PAGE = "/controller?command=mainpage";
    public static final  String SESSION_FIELD_USER = "user";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);


        if (session != null) {
            session.removeAttribute(SESSION_FIELD_USER);
        }

        try {
            response.sendRedirect(REDIRECT_PAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
