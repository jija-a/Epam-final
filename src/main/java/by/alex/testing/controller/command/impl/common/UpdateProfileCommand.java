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
import java.util.List;

public class UpdateProfileCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(UpdateProfileCommand.class);

    private final UserService userService;

    public UpdateProfileCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("Update profile command received");
        ViewResolver resolver;

        String firstName = req.getParameter(RequestConstant.FIRST_NAME);
        String lastName = req.getParameter(RequestConstant.LAST_NAME);
        String psw = req.getParameter(RequestConstant.PASSWORD);
        String confPsw = req.getParameter(RequestConstant.CONFIRMATION_PASSWORD);

        if (psw != null && psw.equals(confPsw)) {
            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute(RequestConstant.USER);

            User updatedUser = userService.findUserById(currentUser.getId());
            updatedUser.setFirstName(firstName);
            updatedUser.setLastName(lastName);
            updatedUser.setPassword(psw.toCharArray());

            List<String> errors = userService.updateUserProfile(updatedUser);
            if (errors.isEmpty()) {
                session.removeAttribute(RequestConstant.USER);
                session.setAttribute(RequestConstant.USER, updatedUser);
                String page = createRedirectURL(req, CommandName.TO_PROFILE_PAGE);
                resolver = new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
                logger.info("User profile info successfully changed");
            } else {
                req.setAttribute(RequestConstant.ERRORS, errors);
                resolver = new ViewResolver(PageConstant.PROFILE_PAGE);
                logger.info("User input wrong data while updating profile");
            }
        } else {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.CONFIRMATION_PSW_ERROR));
            resolver = new ViewResolver(PageConstant.PROFILE_PAGE);
            logger.info("Wrong confirmation password while registering");
        }

        return resolver;
    }
}
