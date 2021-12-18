package by.alex.testing.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter protect application from XSS attacks.
 */
public final class XSSFilter extends BaseFilter {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        LOGGER.info("XSS Filter processing");
        HttpServletRequest req = (HttpServletRequest) request;
        chain.doFilter(new XSSRequestWrapper(req), response);
    }
}
