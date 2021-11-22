package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogInCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(LogInCommand.class);

    private final UserService userService;

    public LogInCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("Login command received");
        ViewResolver resolver;

        String login = req.getParameter(RequestConstant.LOGIN);
        String password = req.getParameter(RequestConstant.PASSWORD);

        User user = userService.login(login, password);
        if (user != null) {
            HttpSession session = req.getSession(false);
            session.setAttribute(RequestConstant.USER, user);

            String page = createRedirectURL(req, CommandName.TO_HOME_PAGE);
            resolver = new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
            logger.info("User successfully logged in");
        } else {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.LOGIN_ERROR));
            resolver = new ViewResolver(PageConstant.LOGIN_PAGE);
            logger.info("Wrong login or password by user");
        }

        logger.info("To course creation page command received");
        return resolver;
    }

}