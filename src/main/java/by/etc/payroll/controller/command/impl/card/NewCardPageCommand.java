package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.*;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.QueryUtil;
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
    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?commad=loginpage";

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/new_card.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {
            User user = (User)request.getSession().getAttribute("user");
            if (!Validator.validateUser(user)) {
                throw new ServiceUnauthorizedAccessException("Incorrect access");
            }

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteCardService concreteCardService = serviceFactory.getCardService();
            ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();

            List<Rate> listRate =  concreteCardService.getAllRate();
            List<Valute> listValute = concreteCardService.getAllValute();
            List<BankAccount> listBankAccount = bankAccountService.getCardsByUserID(user);
            List<Company> listCompany = concreteCardService.getAllCompany();


            request.setAttribute("listRate", listRate);
            request.setAttribute("listValute", listValute);
            request.setAttribute("listBankAccount", listBankAccount);
            request.setAttribute("listCompany", listCompany);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
