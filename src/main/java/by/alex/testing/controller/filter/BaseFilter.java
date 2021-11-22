package by.alex.testing.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public abstract class BaseFilter implements Filter {

    private static final Logger logger =
            LoggerFactory.getLogger(BaseFilter.class);

    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initialize filter: {}", getClass().getSimpleName());
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        logger.info("Destroy filter: {}", getClass().getSimpleName());
    }
}
