package by.etc.payroll.controller.command.impl.card;

import by.etc.payroll.bean.*;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.*;
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


    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/show_card.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(Attributes.SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
        User user = (User)request.getSession().getAttribute(Attributes.SESSION_FIELD_ROLE_USER);

        try {

            UserUtil.isUser(user);

        String carId = request.getParameter(Attributes.REQUSET_CARD_ID);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ConcreteCardService cardService = serviceFactory.getCardService();
        ConcreteBankAccountService bankAccountService = serviceFactory.getBankAccountService();


        Card card = cardService.getCard(carId);
        BankAccount bankAccount = bankAccountService.getAccountById(card.getIdAccount());

        if (bankAccount.getUserId() != user.getId()) {
            if (!user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
                throw new ServiceUnauthorizedAccessException();
            }
        }

        Valute valute = cardService.getValuteById(card.getValute());
        Company company = cardService.getCompanyById(card.getCompany());
        Rate rate = cardService.getRateById(card.getRate());

        boolean status = cardService.checkCardOnBlockByCardId(Integer.valueOf(carId));

        request.setAttribute(Attributes.REQUEST_STATUS, status);
        request.setAttribute(Attributes.REQUEST_RATE, rate);
        request.setAttribute(Attributes.REQUEST_COMPANY, company);
        request.setAttribute(Attributes.REQUEST_VALUTE, valute);

        request.setAttribute(Attributes.REQUEST_ACCOUNT, bankAccount);
        request.setAttribute(Attributes.REQUEST_CARD, card);
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(Message.INCORRECT_ACCESS, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_AFTER_INCORRECT_ACCESS);
        } catch (ServiceWrongCardNumber e) {
            LOG.error(Message.INCORRECT_QUERY, e);
            response.sendRedirect(Pages.REDIRECT_PAGE_INCORRECT_QUERY);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
