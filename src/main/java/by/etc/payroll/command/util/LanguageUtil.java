package by.etc.payroll.command.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LanguageUtil {
    private static final String SESSION_LANGUAGE_ID = "languageId";

    private static final String DEFAULT_LANGUAGE_ID = "RU";
    private static final String ENGLISH_LANGUAGE = "en_US";
    public static final String RUSSIAN_LANGUAGE = "ru_RU";

    /**
     * Determines the current language id for the request.
     *
     * @param request a request object
     * @return the current language id
     */
    public static String getLanguageId(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        String sessionLanguageId = DEFAULT_LANGUAGE_ID;


        if (session != null && session.getAttribute(SESSION_LANGUAGE_ID) != null) {
            sessionLanguageId = (String) session.getAttribute(SESSION_LANGUAGE_ID);
        }

        String languageId = (sessionLanguageId.equalsIgnoreCase(DEFAULT_LANGUAGE_ID)) ? RUSSIAN_LANGUAGE : ENGLISH_LANGUAGE;

        return languageId;
    }
}
