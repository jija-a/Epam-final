package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    public static final Logger logger =
            LoggerFactory.getLogger(ChangeLocaleCommand.class);

    private static final int MAX_AGE = 60 * 60 * 24 * 30;

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp) {

        logger.info("Change locale command received");

        String lang = req.getParameter(RequestConstant.LOCALE);
        Locale locale = LocaleManager.resolveLocale(lang);

        Cookie cookie = new Cookie(RequestConstant.LOCALE, locale.getLanguage());
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath(req.getContextPath());
        resp.addCookie(cookie);
        logger.info("Cookie lang set");

        req.getSession().setAttribute(RequestConstant.LOCALE, locale);
        MessageManager.INSTANCE.changeLocale(locale);

        String page = getRedirectPage(req);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }

    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter(RequestConstant.LANG_REDIRECT_PARAMS);
        String redirectUrl = request.getParameter(RequestConstant.LANG_REDIRECT_URL);
        if (!StringUtils.isNullOrEmpty(redirectPage)) {
            return request.getServletPath() + "?" + redirectPage;
        } else if (!StringUtils.isNullOrEmpty(redirectUrl)) {
            return redirectUrl;
        } else {
            return createRedirectURL(request, CommandName.TO_HOME_PAGE);
        }
    }
}
