package by.alex.testing.controller.filter;

import by.alex.testing.controller.LocaleManager;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * Filter resolves user locale.
 * Set it to default if {@link by.alex.testing.domain.User}
 * currently do not have locale.
 * Set it to {@link Cookie} and {@link HttpSession}.
 *
 * @see LocaleManager
 * @see Locale
 */
public final class LocaleFilter extends BaseFilter {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(LocaleFilter.class);

    /**
     * Default locale. Located in web.xml file.
     */
    private String defaultLocale;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        defaultLocale = filterConfig.getInitParameter("default-locale");
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        LOGGER.debug("Locale filter processing request");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        this.dealWithLocale(req, resp);
        chain.doFilter(req, response);
    }

    private void dealWithLocale(final HttpServletRequest req,
                                final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Locale locale;
        Object lang = session.getAttribute(RequestConstant.LOCALE);
        if (lang == null) {
            LOGGER.info("Session attribute locale is null");
            Cookie[] cookies = req.getCookies();
            if (cookies == null) {
                LOGGER.info("Cookies is null");
                this.setDefaultLocale(session, resp);
            } else {
                Optional<Cookie> optionalCookie = Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(RequestConstant.LOCALE))
                        .findAny();
                if (optionalCookie.isPresent()) {
                    Cookie cookie = optionalCookie.get();
                    locale = LocaleManager.resolveLocale(cookie.getValue());
                    session.setAttribute(RequestConstant.LOCALE, locale);
                    MessageManager.INSTANCE.changeLocale(locale);
                } else {
                    this.setDefaultLocale(session, resp);
                }
            }
        }
        locale = (Locale) session.getAttribute(RequestConstant.LOCALE);
        LOGGER.info("Current locale: {}", locale);
    }

    private void setDefaultLocale(final HttpSession session,
                                  final HttpServletResponse resp) {
        LOGGER.info("Setting default locale - {}", defaultLocale);
        Locale locale = LocaleManager.resolveLocale(defaultLocale);
        Cookie langCookie = new Cookie(RequestConstant.LOCALE, defaultLocale);
        resp.addCookie(langCookie);
        session.setAttribute(RequestConstant.LOCALE, locale);
        MessageManager.INSTANCE.changeLocale(new Locale(defaultLocale));
    }
}
