package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.UnknownEntityException;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class RegistrationCommand implements Command {

    /**
     * @see UserService
     */
    private final UserService userService;

    /**
     * Class constructor. Initializes service.
     */
    public RegistrationCommand() {
        userService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, UnknownEntityException {

        ViewResolver resolver =
                new ViewResolver(PageConstant.REGISTRATION_PAGE);

        String login = req.getParameter(RequestConstant.LOGIN);
        String firstName = req.getParameter(RequestConstant.FIRST_NAME);
        String lastName = req.getParameter(RequestConstant.LAST_NAME);
        String role = req.getParameter(RequestConstant.USER_ROLE);
        String psw = req.getParameter(RequestConstant.PASSWORD);
        String confPsw =
                req.getParameter(RequestConstant.CONFIRMATION_PASSWORD);

        if (userService.findUserByLogin(login) == null) {
            if (!StringUtils.isNullOrEmpty(psw) && psw.equals(confPsw)) {

                User user = User.builder()
                        .login(login)
                        .password(psw.toCharArray())
                        .firstName(firstName)
                        .lastName(lastName)
                        .role(UserRole.resolveRoleById(Integer.parseInt(role)))
                        .build();

                List<String> errors = userService.register(user);
                if (errors.isEmpty()) {
                    String msg = MessageManager.INSTANCE
                            .getMessage(MessageConstant.REGISTRATION_SUCCESS);
                    req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
                    String page = createRedirectURL(req,
                            CommandName.TO_LOGIN_PAGE);
                    resolver = new ViewResolver(page,
                            ViewResolver.ResolveAction.REDIRECT);
                } else {
                    req.setAttribute(RequestConstant.ERRORS, errors);
                }
            } else {
                String msg = MessageManager.INSTANCE
                        .getMessage(MessageConstant.CONFIRMATION_PSW_ERROR);
                req.setAttribute(RequestConstant.ERROR, msg);
            }
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.LOGIN_IS_TAKEN_ERROR);
            req.setAttribute(RequestConstant.ERROR, msg);
        }

        return resolver;
    }

}
