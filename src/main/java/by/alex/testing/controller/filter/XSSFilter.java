package by.alex.testing.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class XSSFilter extends BaseFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        logger.debug("XSS Filter initialized");
    }

    @Override
    public void destroy() {
        logger.debug("XSS Filter destroyed");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.info("XSS Filter processing");
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }
}
