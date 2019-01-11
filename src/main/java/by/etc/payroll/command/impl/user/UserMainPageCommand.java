package by.etc.payroll.command.impl.user;

import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.service.exception.ServiceException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.service.factory.ServiceFactory;
import by.etc.payroll.service.impl.ConcreteUserService;
import by.etc.payroll.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserMainPageCommand implements ActionCommand {
    private Logger LOG = LogManager.getLogger(UserMainPageCommand.class);
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/home/main.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            User user = (User) request.getSession().getAttribute("user");

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ConcreteUserService userService = serviceFactory.getUserService();

            List<BankAccount> bankAccountList = userService.getListBankAccountByUser(user);
            request.setAttribute("listBankAccount", bankAccountList);


            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceUnauthorizedAccessException e) {
            LOG.error(e.getMessage(), e);
            response.sendRedirect("/controller?command=loginpage");
        } catch (ServletException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
