package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class LogInCommand implements Command {

    /**
     * @see UserService
     */
    private final UserService userService;

    /**
     * Class constructor. Initializes service.
     */
    public LogInCommand() {
        userService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        ViewResolver resolver;
        String login = req.getParameter(RequestConstant.LOGIN);
        String password = req.getParameter(RequestConstant.PASSWORD);
        User user = userService.login(login, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute(RequestConstant.USER, user);

            String page = createRedirectURL(req, CommandName.TO_HOME_PAGE);
            resolver =
                    new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.LOGIN_ERROR);
            req.setAttribute(RequestConstant.ERROR, msg);
            resolver = new ViewResolver(PageConstant.LOGIN_PAGE);
        }
        return resolver;
    }
}
