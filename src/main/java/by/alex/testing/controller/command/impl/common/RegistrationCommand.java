package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RegistrationCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(RegistrationCommand.class);

    private final UserService userService;

    public RegistrationCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("Register command received");
        ViewResolver resolver;

        String login = req.getParameter(RequestConstant.LOGIN);
        String firstName = req.getParameter(RequestConstant.FIRST_NAME);
        String lastName = req.getParameter(RequestConstant.LAST_NAME);
        String role = req.getParameter(RequestConstant.USER_ROLE);
        String psw = req.getParameter(RequestConstant.PASSWORD);
        String confPsw = req.getParameter(RequestConstant.CONFIRMATION_PASSWORD);

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
                    req.getSession().setAttribute(RequestConstant.SUCCESS,
                            MessageManager.INSTANCE.getMessage(MessageConstant.REGISTRATION_SUCCESS));
                    String page = createRedirectURL(req, CommandName.TO_LOGIN_PAGE);
                    resolver = new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
                    logger.info("User successfully registered");
                } else {
                    req.setAttribute(RequestConstant.ERRORS, errors);
                    resolver = new ViewResolver(PageConstant.REGISTRATION_PAGE);
                    logger.info("Wrong input data from user while registering");
                }

            } else {
                req.setAttribute(RequestConstant.ERROR,
                        MessageManager.INSTANCE.getMessage(MessageConstant.CONFIRMATION_PSW_ERROR));
                resolver = new ViewResolver(PageConstant.REGISTRATION_PAGE);
                logger.info("Wrong confirmation password");
            }
        } else {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.LOGIN_IS_TAKEN_ERROR));
            resolver = new ViewResolver(PageConstant.REGISTRATION_PAGE);
            logger.info("Input already registered user login");
        }

        return resolver;
    }

}
