package by.etc.payroll.controller.command.impl.transfer;

import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.User;
import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.util.LanguageUtil;
import by.etc.payroll.controller.command.util.QueryUtil;
import by.etc.payroll.controller.command.util.UserUtil;
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

public class TransferMoneyCardCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(TransferMoneyCardCommand.class);

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?command=mainPage&useraccess=true";

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/transfer_card_money.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        User user = (User)request.getSession().getAttribute("user");

        try {

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteCardService concreteCardService = serviceFactory.getCardService();
            List<Card> cardList = concreteCardService.getAllCardByUser(user);

            request.setAttribute("cardList", cardList);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error("Incorrect access", e);
            response.sendRedirect(REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        }

    }
}
