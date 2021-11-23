package by.alex.testing.controller.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListenerImpl implements HttpSessionListener, HttpSessionAttributeListener {

    private static final Logger logger =
            LoggerFactory.getLogger(SessionListenerImpl.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent ev) {
        logger.info("add: {} : {} : {}", ev.getClass().getSimpleName(),
                ev.getName(), ev.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent ev) {
        logger.info("remove: {} : {} : {}", ev.getClass().getSimpleName(),
                ev.getName(), ev.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent ev) {
        logger.info("replace: {} : {} : {}", ev.getClass().getSimpleName(),
                ev.getName(), ev.getValue());
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.debug("Session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.debug("Session destroyed");
    }
}
