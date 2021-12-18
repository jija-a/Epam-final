package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.HashService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class UpdateProfileCommand implements Command {

    /**
     * @see UserService
     */
    private final UserService userService;

    /**
     * Class constructor. Initializes service.
     */
    public UpdateProfileCommand() {
        userService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        ViewResolver resolver;
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(RequestConstant.USER);

        String firstName = req.getParameter(RequestConstant.FIRST_NAME);
        String lastName = req.getParameter(RequestConstant.LAST_NAME);
        String psw = req.getParameter(RequestConstant.PASSWORD);
        String confPsw =
                req.getParameter(RequestConstant.CONFIRMATION_PASSWORD);

        if (psw != null && psw.equals(confPsw)
                && HashService.check(psw, currentUser.getPassword())) {

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);

            if (userService.updateUserProfile(currentUser)) {

                String page =
                        createRedirectURL(req, CommandName.TO_PROFILE_PAGE);
                resolver = new ViewResolver(page,
                        ViewResolver.ResolveAction.REDIRECT);
                String msg = MessageManager.INSTANCE
                        .getMessage(MessageConstant.UPDATED_SUCCESS);
                req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            } else {
                String msg = MessageManager.INSTANCE
                        .getMessage(MessageConstant.UPDATE_ERROR);
                req.setAttribute(RequestConstant.ERROR, msg);
                resolver = new ViewResolver(PageConstant.PROFILE_PAGE);
            }
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.CONFIRMATION_PSW_ERROR);
            req.setAttribute(RequestConstant.ERROR, msg);
            resolver = new ViewResolver(PageConstant.PROFILE_PAGE);
        }

        return resolver;
    }
}
