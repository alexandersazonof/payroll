package by.etc.payroll.controller.command.impl.account;

import by.etc.payroll.bean.User;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NewAccountPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(NewAccountPageCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {

            User user = (User) request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
            UserUtil.isUser(user);

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteCardService concreteCardService = serviceFactory.getCardService();

            List<Valute> valuteList = concreteCardService.getAllValute();


            request.setAttribute(Attributes.REQUEST_VALUTE_LIST, valuteList);
            request.getRequestDispatcher(Pages.JSP_NEW_ACCOUNT_PAGE).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServletException e) {
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
