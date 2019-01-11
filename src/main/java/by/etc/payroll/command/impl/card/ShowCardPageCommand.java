package by.etc.payroll.command.impl.card;

import by.etc.payroll.bean.User;
import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.util.LanguageUtil;
import by.etc.payroll.command.util.QueryUtil;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.service.exception.ServiceUnauthorizedAccessException;
import by.etc.payroll.util.Roles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCardPageCommand implements ActionCommand {
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String REDIRECT_PAGE_AFTER_UNAVTARIZED_ACCESS = "/controller?commad=loginpage";

    private static final String CARD_ID_REQUEST_ATTR = "cid";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
        User user = (User)request.getSession().getAttribute("user");

        try {
        // add show only admin and user owns the card
        if (user == null || user.getRole().equalsIgnoreCase(Roles.ADMIN)) {
                throw new ServiceUnauthorizedAccessException("Incorrect acccess");
        }

        String carId = request.getParameter(CARD_ID_REQUEST_ATTR);


        } catch (ServiceUnauthorizedAccessException e) {
            e.printStackTrace();
        }
    }
}
