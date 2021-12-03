package by.alex.testing.controller.filter;

import by.alex.testing.controller.LocaleManager;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class LocaleFilter extends BaseFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(LocaleFilter.class);

    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        defaultLocale = filterConfig.getInitParameter("default-locale");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.debug("Locale filter processing request");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        this.dealWithLocale(req, resp);
        chain.doFilter(req, response);
    }

    private void dealWithLocale(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Locale locale;
        Object lang = session.getAttribute(RequestConstant.LOCALE);
        if (lang == null) {
            logger.info("Session attribute locale is null");
            Cookie[] cookies = req.getCookies();
            if (cookies == null) {
                logger.info("Cookies is null");
                this.setDefaultLocale(session, resp);
            } else {
                Optional<Cookie> cookie = Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(RequestConstant.LOCALE))
                        .findAny();
                if (cookie.isPresent()) {
                    locale = LocaleManager.resolveLocale(cookie.get().getValue());
                    session.setAttribute(RequestConstant.LOCALE, locale);
                    MessageManager.INSTANCE.changeLocale(locale);
                } else {
                    this.setDefaultLocale(session, resp);
                }
            }
        }
        locale = (Locale) session.getAttribute(RequestConstant.LOCALE);
        logger.info("Current locale: {}", locale);
    }

    private void setDefaultLocale(HttpSession session, HttpServletResponse resp) {
        logger.info("Setting default locale - {}", defaultLocale);
        Locale locale = LocaleManager.resolveLocale(defaultLocale);
        Cookie langCookie = new Cookie(RequestConstant.LOCALE, defaultLocale);
        resp.addCookie(langCookie);
        session.setAttribute(RequestConstant.LOCALE, locale);
        MessageManager.INSTANCE.changeLocale(new Locale(defaultLocale));
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
