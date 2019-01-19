package by.etc.payroll.controller.command.impl.general;

import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ChangeLanguageCommand implements ActionCommand {

    private static final String CHANGE_LANGUAGE_PARAMETER = "selectLanguage";
    private static final String SESSION_LANGUAGE_ID = "languageId";
    private static final String SESSION_PREV_QUERY = "prevQuery";
    private static final String WELCOME_PAGE = "/controller?command=mainPage";

    private ArrayList<String> availableLanguages = new ArrayList<>();
    private static final String ENGLISH = "EN";
    private static final String RUSSIAN = "RU";

    public ChangeLanguageCommand(){
        availableLanguages.add(ENGLISH);
        availableLanguages.add(RUSSIAN);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        String language = request.getParameter(CHANGE_LANGUAGE_PARAMETER);


        HttpSession session = request.getSession();

        if (language != null) {
            if (!availableLanguages.contains(language)) {


                language = RUSSIAN;
            }
            session.setAttribute(SESSION_LANGUAGE_ID, language);
        }

        String prevQuery = (String) session.getAttribute(SESSION_PREV_QUERY);
        if(prevQuery == null){
            prevQuery = WELCOME_PAGE;
        }

        response.sendRedirect(prevQuery);



    }
}
