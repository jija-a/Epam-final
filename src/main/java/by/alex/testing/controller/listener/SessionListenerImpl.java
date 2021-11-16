package by.alex.testing.controller.listener;

import by.alex.testing.controller.RequestConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.util.Locale;

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
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        logger.info("Default locale set");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().invalidate();
        logger.info("Session destroyed");
    }
}
