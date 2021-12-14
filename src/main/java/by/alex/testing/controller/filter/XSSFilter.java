package by.alex.testing.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XSSFilter extends BaseFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.info("XSS Filter processing");
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }
}
