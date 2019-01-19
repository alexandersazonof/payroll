package by.etc.payroll.controller.command.impl.admin;

import by.etc.payroll.bean.ExchangeRate;
import by.etc.payroll.bean.User;
import by.etc.payroll.bean.Valute;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.AbstractCardService;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceQueryException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EditValutePageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(EditValutePageCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


        User admin = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String exchangeId = request.getParameter(Attributes.REQUEST_EXACHANGE_RATE_ID);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractCardService cardService = serviceFactory.getCardService();

        try {
            UserUtil.isAdmin(admin);
            ExchangeRate exchangeRate = cardService.getExchangeRateById(exchangeId);

            String fromValute = cardService.getValuteNameById(exchangeRate.getFromValuteId());
            String toValute = cardService.getValuteNameById(exchangeRate.getToValuteId());

            request.setAttribute(Attributes.REQUEST_EXACHANGE_RATE, exchangeRate);
            request.setAttribute(Attributes.REQUEST_FROM_VALUTE, fromValute);
            request.setAttribute(Attributes.REQUSET_TO_VALUTE, toValute);

            request.getRequestDispatcher(Pages.JSP_ADMIN_EDIT_VALUTE).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceQueryException e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
