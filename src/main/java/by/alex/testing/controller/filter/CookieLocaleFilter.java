package by.alex.testing.controller.filter;

import by.alex.testing.controller.RequestConstant;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieLocaleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getParameter(RequestConstant.LOCALE) != null) {
            Cookie cookie = new Cookie(RequestConstant.LANG, req.getParameter(RequestConstant.LOCALE));
            res.addCookie(cookie);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
