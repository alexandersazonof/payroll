package by.etc.payroll.command.impl.general;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.service.exception.ServiceWrongEmailException;
import by.etc.payroll.service.exception.ServiceWrongLoginException;
import by.etc.payroll.service.exception.ServiceWrongPasswordException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.command.util.Attributes;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.impl.ConcreteUserService;
import by.etc.payroll.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(SignUpCommand.class);


    private static final String REDIRECT_PAGE_AFTER_ERROR = "/controller?command=signuppage&";
    private static final String REDIRECT_PAGE_AFTER_SIGNUP = "/controller?command=mainpage";

    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String WRONG_EMAIL_REQUEST_ATTR = "wrongEmail";
    private static final String WRONG_VALUE_REQUEST_ATTR = "wrongValue";

    private static final String AMP = "&";
    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        LOG.info("Sign up command");

        User user = null;

            LOG.info("Begin processing registry form..");
            String login = request.getParameter(Attributes.FIELD_USERNAME);
            String email = request.getParameter(Attributes.FIELD_EMAIL);
            String password = request.getParameter(Attributes.FIELD_PASSWORD);
            String passwordConfirm = request.getParameter(Attributes.FIELD_CONFIRM_PASSWORD);
            String firstName = request.getParameter(Attributes.FIELD_FIRSTNAME);
            String lastName = request.getParameter(Attributes.FIELD_LASTNAME);


           if (login != null && email != null && password != null && passwordConfirm != null
                   && firstName != null && lastName != null) {

               try {
                   if (!password.equals(passwordConfirm)) {
                       throw new ServiceWrongPasswordException("Incorrect password");
                   }
                   ServiceFactory serviceFactory = ServiceFactory.getInstance();
                   ConcreteUserService userService = serviceFactory.getUserService();

                   userService.registration(create(login, password, email, firstName, lastName));
                   response.sendRedirect(REDIRECT_PAGE_AFTER_SIGNUP);



               } catch (ServiceWrongEmailException e) {
                   LOG.error(e);

                   response.sendRedirect(makeErrorRedirectString(WRONG_EMAIL_REQUEST_ATTR, login,
                           email, firstName, lastName));

               } catch (ServiceWrongLoginException e) {
                   LOG.error(e);
                   response.sendRedirect(makeErrorRedirectString(WRONG_LOGIN_REQUEST_ATTR, login,
                           email, firstName, lastName));

               } catch (ServiceWrongPasswordException e) {
                   response.sendRedirect(makeErrorRedirectString(WRONG_PASSWORD_REQUEST_ATTR, login,
                           email, firstName, lastName));
                   LOG.error(e);

               } catch (ServiceException e) {
                   response.sendRedirect(makeErrorRedirectString(WRONG_VALUE_REQUEST_ATTR, login,
                           email, firstName, lastName));

               } catch (IOException e) {

                   LOG.error(e.getMessage(), e);
                   throw new CommandException(e);
               }
           }
    }

    private String makeErrorRedirectString(String errorName, String login, String email,
                                           String firstName, String lastName) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_PAGE_AFTER_ERROR);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(Attributes.FIELD_USERNAME);
        parameters.append(EQ);
        parameters.append(login);
        parameters.append(AMP);
        parameters.append(Attributes.FIELD_EMAIL);
        parameters.append(EQ);
        parameters.append(email);
        parameters.append(AMP);
        parameters.append(Attributes.FIELD_FIRSTNAME);
        parameters.append(EQ);
        parameters.append(firstName);
        parameters.append(AMP);
        parameters.append(Attributes.FIELD_LASTNAME);
        parameters.append(EQ);
        parameters.append(lastName);

        return parameters.toString();
    }
    private User create (String login, String password, String email, String lastName, String firstName)  {
        User user = new User();

        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setRole(Roles.USER);
        return user;
    }
}
