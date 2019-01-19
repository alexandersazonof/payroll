package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractCardService;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewCardCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(NewCardCommand.class);


    private static final String REDIRECT_WRONG_VALUE = "/controller?command=newcardpage&";

    private static final String SUCCESS_NEW_CARD = "/controller?command=mainpage&successcard=true";

    private static final String WRONG_CHECKBOX = "msgbox";
    private static final String WRONG_VALUE = "msgvalue";
    private static final String WRONG_BLOCK = "block";

    private static final String FIRST_NAME_REQUEST_ATTR = "firstName";
    private static final String LAST_NAME_REQUEST_ATTR = "lastName";
    private static final String ADDRESS_NAME_REQUEST_ATTR = "address";
    private static final String CITY_NAME_REQUSET_ATTR = "city";

    private static final String AMP = "&";
    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


        String firstName = request.getParameter(FIRST_NAME_REQUEST_ATTR);
        String lastName = request.getParameter(LAST_NAME_REQUEST_ATTR);
        String address = request.getParameter(ADDRESS_NAME_REQUEST_ATTR);
        String city = request.getParameter(CITY_NAME_REQUSET_ATTR);
        String company = request.getParameter(Attributes.REQUEST_COMPANY);

        try {
            User user = (User) request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
            UserUtil.isUser(user);


            if (request.getParameter(Attributes.REQUEST_RULES) == null) {
                throw new ServiceWrongBoxException(Message.INCORRECT_VALUE);
            }

            String bankAccount = request.getParameter(Attributes.REQUEST_ACCOUNT);
            String rate = request.getParameter(Attributes.REQUEST_RATE);


            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            AbstractCardService cardService = serviceFactory.getCardService();

            cardService.addCard(firstName, lastName, address, city, bankAccount, rate, company, user.getId());
            response.sendRedirect(SUCCESS_NEW_CARD);

        }catch (ServiceBlockAccountException e){
            LOG.error(Message.ALERT_ACCOUNT_IS_BLOCK, e);
            response.sendRedirect(makeErrorRedirectString(WRONG_BLOCK, firstName, lastName, address, city));
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceWrongBoxException e) {
            LOG.error(Message.TOUCH_BOX, e);
            response.sendRedirect(makeErrorRedirectString(WRONG_CHECKBOX, firstName, lastName, address, city));
        } catch (ServiceWrongNameException e) {
            LOG.error(Message.INCORRECT_VALUE, e);
            response.sendRedirect(makeErrorRedirectString(WRONG_VALUE, firstName, lastName, address, city));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }


    }

    private String makeErrorRedirectString(String errorName, String firstName, String lastName, String address, String city) {

        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_WRONG_VALUE);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(FIRST_NAME_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(firstName);
        parameters.append(AMP);
        parameters.append(LAST_NAME_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(lastName);
        parameters.append(AMP);
        parameters.append(ADDRESS_NAME_REQUEST_ATTR);
        parameters.append(EQ);
        parameters.append(address);
        parameters.append(AMP);
        parameters.append(CITY_NAME_REQUSET_ATTR);
        parameters.append(EQ);
        parameters.append(city);
        parameters.append(AMP);

        return parameters.toString();

    }
}
