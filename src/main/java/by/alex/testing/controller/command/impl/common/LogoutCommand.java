package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            logger.info("Session invalidated");
        }

        logger.info("Logout command received");
        return new ViewResolver(PageConstant.LOGIN_PAGE);
    }
}
