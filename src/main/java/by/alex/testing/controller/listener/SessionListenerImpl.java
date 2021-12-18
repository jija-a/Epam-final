package by.alex.testing.controller.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Filter listens and logg when session starts/destroys.
 * Also, everything that was added/removed/updated in session.
 */
@WebListener
public final class SessionListenerImpl implements
        HttpSessionListener, HttpSessionAttributeListener {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SessionListenerImpl.class);

    @Override
    public void attributeAdded(final HttpSessionBindingEvent ev) {
        LOGGER.info("add: {} : {} : {}", ev.getClass().getSimpleName(),
                ev.getName(), ev.getValue());
    }

    @Override
    public void attributeRemoved(final HttpSessionBindingEvent ev) {
        LOGGER.info("remove: {} : {} : {}", ev.getClass().getSimpleName(),
                ev.getName(), ev.getValue());
    }

    @Override
    public void attributeReplaced(final HttpSessionBindingEvent ev) {
        LOGGER.info("replace: {} : {} : {}", ev.getClass().getSimpleName(),
                ev.getName(), ev.getValue());
    }

    @Override
    public void sessionCreated(final HttpSessionEvent event) {
        LOGGER.debug("Session created");
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        LOGGER.debug("Session destroyed");
    }
}
