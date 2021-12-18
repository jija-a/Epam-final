package by.alex.testing.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * Base filter. Provides default implementation for
 * {@link Filter#init(FilterConfig)} and {@link Filter#destroy()}
 * methods.
 */
public abstract class BaseFilter implements Filter {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(BaseFilter.class);

    /**
     * @see FilterConfig
     */
    protected FilterConfig filterConfig;

    /**
     * Method initializes {@link BaseFilter} and extensions.
     *
     * @param filterConfig {@link FilterConfig}
     * @throws ServletException
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Initialize filter: {}", getClass().getSimpleName());
        this.filterConfig = filterConfig;
    }

    /**
     * Method calls when filters destroyed.
     */
    @Override
    public void destroy() {
        LOGGER.info("Destroy filter: {}", getClass().getSimpleName());
    }
}
