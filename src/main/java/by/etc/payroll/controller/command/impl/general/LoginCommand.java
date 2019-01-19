package by.etc.payroll.controller.command.impl.general;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.service.exception.ServiceWrongLoginException;
import by.etc.payroll.service.exception.ServiceWrongPasswordException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.controller.command.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.impl.ConcreteUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements ActionCommand {


    private Logger LOG = LogManager.getLogger(LoginCommand.class);

    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=loginpage&";
    private static final String REDIRECT_PAGE_AFTER_LOGIN = "/controller?command=mainpage";


    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String LOGIN_FORM_NAME  = "login";



    private static final String AMP = "&";
    private static final String EQ = "=";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        String login = request.getParameter(Attributes.FIELD_LOGIN);
        String password = request.getParameter(Attributes.FIELD_PASSWORD);


        User user = null;
        try {

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteUserService userService = serviceFactory.getUserService();

            user = userService.logIn(login, password);

            HttpSession session = request.getSession(true);
            user.setPassword("");
            session.setAttribute(Attributes.FIELD_USER, user);

            response.sendRedirect(REDIRECT_PAGE_AFTER_LOGIN);

        } catch (ServiceWrongLoginException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(makeErrorRedirectString(WRONG_LOGIN_REQUEST_ATTR, login));

        } catch (ServiceWrongPasswordException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(makeErrorRedirectString(WRONG_PASSWORD_REQUEST_ATTR, login));

        } catch (ServiceException e) {
            throw new CommandException();
        }
    }

    private String makeErrorRedirectString(String errorName, String login) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_PAGE_AFTER_ERROR);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(LOGIN_FORM_NAME);
        parameters.append(EQ);
        parameters.append(login);

        return parameters.toString();
    }
}
