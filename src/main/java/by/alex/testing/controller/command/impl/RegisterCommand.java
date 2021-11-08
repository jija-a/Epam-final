package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.UserService;
import by.alex.testing.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {

    private final UserService service;

    public RegisterCommand() {
        this.service = new UserServiceImpl();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String login = req.getParameter(RequestConstant.LOGIN);
        User existing = service.findUserByLogin(login);

        if (existing == null) {
            String password = req.getParameter(RequestConstant.PASSWORD);
            String firstName = req.getParameter(RequestConstant.FIRST_NAME);
            String lastName = req.getParameter(RequestConstant.LAST_NAME);
            int roleId = Integer.parseInt(req.getParameter(RequestConstant.USER_ROLE));
            UserRole role = UserRole.resolveRoleById(roleId);
            System.out.println(role.toString());
            User user = new User(login, firstName, lastName, password.toCharArray(), role);

            service.register(user);
            req.getSession(false).setAttribute(RequestConstant.USER, user);
        } else {
            req.setAttribute(RequestConstant.ERROR, "Login is taken");
            return new ViewResolver(PageConstant.SIGN_UP_PAGE);
        }

        return new ViewResolver(PageConstant.HOME_PAGE, ViewResolver.ResolveAction.REDIRECT);
    }
}
