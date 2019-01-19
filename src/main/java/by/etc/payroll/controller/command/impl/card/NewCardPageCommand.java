package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.*;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import by.etc.payroll.service.impl.ConcreteCardService;
import by.etc.payroll.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NewCardPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(NewCardPageCommand.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/new_card.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {
            User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);
            UserUtil.isUser(user);


            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteCardService concreteCardService = serviceFactory.getCardService();
            ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            List<Rate> listRate =  concreteCardService.getAllRate();
            List<Valute> listValute = concreteCardService.getAllValute();
            List<BankAccount> listBankAccount = bankAccountService.getCardsByUserID(user);
            List<Company> listCompany = concreteCardService.getAllCompany();


            request.setAttribute(Attributes.REQUEST_LIST_RATE, listRate);
            request.setAttribute(Attributes.REQUEST_LIST_VALUTE, listValute);
            request.setAttribute(Attributes.REQUEST_LIST_BANK_ACCOUNT, listBankAccount);
            request.setAttribute(Attributes.REQUEST_LIST_COMPANY, listCompany);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
