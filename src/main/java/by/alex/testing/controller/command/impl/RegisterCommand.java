package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RegisterCommand implements Command {

    private final UserService userService;

    public RegisterCommand() {
        this.userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        ViewResolver resolver;
        String login = req.getParameter(RequestConstant.LOGIN);
        String password = req.getParameter(RequestConstant.PASSWORD);
        String confPassword = req.getParameter(RequestConstant.CONFIRM_PASSWORD);
        if (!password.equals(confPassword)){
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.PASSWORD_NOT_MATCHES));
            return new ViewResolver(PageConstant.SIGN_UP_PAGE);
        }
        User existing = userService.findUserByLogin(login);

        if (existing == null) {
            String firstName = req.getParameter(RequestConstant.FIRST_NAME);
            String lastName = req.getParameter(RequestConstant.LAST_NAME);
            int roleId = Integer.parseInt(req.getParameter(RequestConstant.USER_ROLE));
            UserRole role = UserRole.resolveRoleById(roleId);

            User user = new User(login, firstName, lastName, password.toCharArray(), role);

            List<String> errors = userService.register(user);
            if (errors.isEmpty()) {
                req.getSession(false).setAttribute(RequestConstant.USER, user);
                resolver = new ViewResolver(PageConstant.HOME_PAGE, ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERRORS, errors);
                resolver = new ViewResolver(PageConstant.SIGN_UP_PAGE);
            }
        } else {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.LOGIN_TAKEN));
            resolver = new ViewResolver(PageConstant.SIGN_UP_PAGE);
        }

        return resolver;
    }
}
