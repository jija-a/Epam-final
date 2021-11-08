package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(RequestConstant.USER);
        }

        return new ViewResolver(PageConstant.HOME_PAGE);
    }
}
