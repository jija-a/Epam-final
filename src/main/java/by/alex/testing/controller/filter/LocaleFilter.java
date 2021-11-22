package by.alex.testing.controller.filter;

import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@WebFilter(filterName = "locale-filter",
        urlPatterns = "/controller/*",
        initParams = {
                @WebInitParam(name = "default-locale", value = "en_US")})
public class LocaleFilter extends BaseFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(LocaleFilter.class);

    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        defaultLocale = filterConfig.getInitParameter("default-locale");
        logger.debug("Locale filter initialized");
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
        Object lang = session.getAttribute(RequestConstant.LOCALE);
        if (lang == null) {
            logger.info("Session attribute locale is null");
            Cookie[] cookies = req.getCookies();
            if (cookies == null) {
                logger.info("Cookies is null");
                this.setDefaultLocale(session, resp);
            } else {
                Optional<Cookie> locale = Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(RequestConstant.LOCALE))
                        .findAny();
                if (locale.isPresent()) {
                    session.setAttribute(RequestConstant.LOCALE, new Locale(locale.get().getValue()));
                } else {
                    this.setDefaultLocale(session, resp);
                }
            }
        } else {
            logger.info("Locale already set");
        }
    }

    private void setDefaultLocale(HttpSession session, HttpServletResponse resp) {
        logger.info("Setting default locale");
        Cookie langCookie = new Cookie(RequestConstant.LOCALE, defaultLocale);
        resp.addCookie(langCookie);
        session.setAttribute(RequestConstant.LOCALE, defaultLocale);
        MessageManager.INSTANCE.changeLocale(new Locale(defaultLocale));
    }

    @Override
    public void destroy() {
        logger.debug("Locale filter destroyed");
    }

}
