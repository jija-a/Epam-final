package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.LocaleManager;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public final class ChangeLocaleCommand implements Command {

    /**
     * Cookies max age (seconds * minutes * hours * days).
     */
    private static final int MAX_AGE = 60 * 60 * 24 * 30;

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp) {
        String lang = req.getParameter(RequestConstant.LOCALE);
        Locale locale = LocaleManager.resolveLocale(lang);

        Cookie cookie =
                new Cookie(RequestConstant.LOCALE, locale.getLanguage());
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath(req.getContextPath());
        resp.addCookie(cookie);

        req.getSession().setAttribute(RequestConstant.LOCALE, locale);
        MessageManager.INSTANCE.changeLocale(locale);

        String page = getRedirectPage(req);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }

    private String getRedirectPage(final HttpServletRequest request) {
        String redirectPage =
                request.getParameter(RequestConstant.LANG_REDIRECT_PARAMS);
        String redirectUrl =
                request.getParameter(RequestConstant.LANG_REDIRECT_URL);
        if (!StringUtils.isNullOrEmpty(redirectPage)) {
            return request.getServletPath() + "?" + redirectPage;
        } else if (!StringUtils.isNullOrEmpty(redirectUrl)) {
            return redirectUrl;
        } else {
            return createRedirectURL(request, CommandName.TO_HOME_PAGE);
        }
    }
}
