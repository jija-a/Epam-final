package by.alex.testing.controller.listener;

import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Filter in order to initialize everything that needs
 * to be initialized when the application starts.
 */
@WebListener
public final class ContextListener implements ServletContextListener {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        ServiceFactory.getInstance();
        LOGGER.debug("Context listener initialized");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
        LOGGER.debug("Context listener destroyed");
    }
}
