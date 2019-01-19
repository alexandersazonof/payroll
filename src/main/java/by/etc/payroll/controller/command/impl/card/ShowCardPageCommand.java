package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.*;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.exception.ServiceWrongCardNumber;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import by.etc.payroll.service.impl.ConcreteCardService;
import by.etc.payroll.service.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCardPageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(ShowCardPageCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";


    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";
    private static final String REDIRECT_PAGE_AFTER_INCORRECT_CARD_ID = "/controller?command=mainPage&incorrectcard=true";


    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/show_card.jsp";

    private static final String CARD_ID_REQUEST_ATTR = "cid";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
        User user = (User)request.getSession().getAttribute("user");

        try {
        if (user == null) {
            throw new ServiceUnauthorizedAccessException("Incorrect acccess");
        }

        String carId = request.getParameter(CARD_ID_REQUEST_ATTR);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ConcreteCardService cardService = serviceFactory.getCardService();
        ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();


        Card card = cardService.getCard(carId);
        BankAccount bankAccount = bankAccountService.getAccountById(card.getIdAccount());

        if (bankAccount.getUserId() != user.getId()) {
            if (!user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
                throw new ServiceUnauthorizedAccessException("Incorrect access");
            }
        }

        Valute valute = cardService.getValuteById(card.getValute());
        Company company = cardService.getCompanyById(card.getCompany());
        Rate rate = cardService.getRateById(card.getRate());

        boolean status = cardService.checkCardOnBlockByCardId(Integer.valueOf(carId));

        request.setAttribute("status", status);
        request.setAttribute("rate", rate);
        request.setAttribute("company", company);
        request.setAttribute("valute", valute);

        request.setAttribute("account", bankAccount);
        request.setAttribute("card", card);
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error("Incorrect access", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceWrongCardNumber e) {
            LOG.error("Incorrect card id", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_INCORRECT_CARD_ID);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
