package by.etc.payroll.controller.command.impl.user;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserCommand implements ActionCommand {
    Logger LOG = LogManager.getLogger(EditUserCommand.class);

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String REDIRECT_PAGE_AFTER_EDIT = "/controller?command=mainpage";
    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=edituserpage&";

    private static final String SESSION_ATTRIBUTE_USER = "user";
    private static final String FORM_FIELD_LOGIN = "login";
    private static final String FORM_FIELD_EMAIL = "email";
    private static final String FORM_FIELD_FIRST_NAME = "firstName";
    private static final String FORM_FIELD_LAST_NAME = "lastName";
    private static final String FORM_FIELD_NEW_PASSWORD = "newPassword";
    private static final String FORM_FIELD_CONFRIM_PASSWORD = "confirmPassword";
    private static final String FORM_FIELD_OLD_PASSWORD = "password";

    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String WRONG_PASSWORD_CONFIRM_REQUEST_ATTR = "wrongPasswordConfirm";
    private static final String WRONG_EMAIL_REQUEST_ATTR = "wrongEmail";
    private static final String WRONG_NAME_REQUEST_ATTR = "wrongName";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {

            LOG.info("In command");
            int id = ((User) request.getSession().getAttribute(SESSION_ATTRIBUTE_USER)).getId();
            String login = request.getParameter(FORM_FIELD_LOGIN);
            String email = request.getParameter(FORM_FIELD_EMAIL);
            String firstName = request.getParameter(FORM_FIELD_FIRST_NAME);
            String lastName = request.getParameter(FORM_FIELD_LAST_NAME);
            String newPassword = request.getParameter(FORM_FIELD_NEW_PASSWORD);
            String confirmPassword = request.getParameter(FORM_FIELD_CONFRIM_PASSWORD);
            String password = request.getParameter(FORM_FIELD_OLD_PASSWORD);

            ServiceFactory factory = ServiceFactory.getInstance();
            ConcreteUserService service = factory.getUserService();


            User user = service.save(id, login, email, firstName, lastName, newPassword, confirmPassword, password);
            request.getSession().setAttribute(SESSION_ATTRIBUTE_USER, user);
            response.sendRedirect(REDIRECT_PAGE_AFTER_EDIT);

        } catch (ServiceWrongLoginException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR + WRONG_LOGIN_REQUEST_ATTR + EQ + true);
        } catch (ServiceWrongEmailException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR + WRONG_EMAIL_REQUEST_ATTR + EQ + true);


        } catch (ServiceWrongNameException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR + WRONG_NAME_REQUEST_ATTR + EQ + true);


        } catch (ServiceWrongPasswordException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR + WRONG_PASSWORD_REQUEST_ATTR + EQ + true);


        } catch (ServiceWrongPasswordConfirmExceprion e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_ERROR + WRONG_PASSWORD_CONFIRM_REQUEST_ATTR + EQ + true);


        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
