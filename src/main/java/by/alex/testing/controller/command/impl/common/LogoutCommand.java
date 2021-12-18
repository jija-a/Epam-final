package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class LogoutCommand implements Command {

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp) {

        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        String page = createRedirectURL(req, CommandName.TO_LOGIN_PAGE);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
