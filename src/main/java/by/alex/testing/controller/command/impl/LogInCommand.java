package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.UserService;
import by.alex.testing.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {

    private final UserService service;

    public LogInCommand() {
        this.service = new UserServiceImpl();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServiceException {

        String login = req.getParameter(RequestConstant.LOGIN);
        String password = req.getParameter(RequestConstant.PASSWORD);

        User user = service.login(login, password);
        if (user != null) {
            req.getSession(false).setAttribute(RequestConstant.USER, user);
            return new ViewResolver(PageConstant.HOME_PAGE);
        } else {
            req.setAttribute(RequestConstant.ERROR, "Wrong login or password");
            return new ViewResolver(PageConstant.LOG_IN_PAGE);
        }
    }
}
