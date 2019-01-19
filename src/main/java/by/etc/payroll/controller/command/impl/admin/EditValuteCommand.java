package by.etc.payroll.controller.command.impl.admin;

import by.etc.payroll.bean.User;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditValuteCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(EditValuteCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);


        User admin = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
        String course = request.getParameter(Attributes.REQUSET_COURSE);
        String id = request.getParameter(Attributes.REQUEST_ID);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AbstractCardService cardService = serviceFactory.getCardService();

        try {
            UserUtil.isAdmin(admin);

            cardService.editValite(id, course);
            response.sendRedirect(Pages.REDIRECT_ADMIN_VALUTE_AFTER_SUCCESS_EDIT);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_QUERY);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceQueryException e) {
            LOG.error(Message.INCORRECT_QUERY);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
