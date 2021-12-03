package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.CommonService;
import by.alex.testing.service.HashService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateProfileCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(UpdateProfileCommand.class);

    private final CommonService commonService;

    public UpdateProfileCommand() {
        commonService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("Update profile command received");
        ViewResolver resolver;
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(RequestConstant.USER);

        String firstName = req.getParameter(RequestConstant.FIRST_NAME);
        String lastName = req.getParameter(RequestConstant.LAST_NAME);
        String psw = req.getParameter(RequestConstant.PASSWORD);
        String confPsw = req.getParameter(RequestConstant.CONFIRMATION_PASSWORD);

        logger.debug("psw - {}, conf - {}, curr - {}", psw, confPsw, HashService.check(psw, currentUser.getPassword()));
        if (psw != null && psw.equals(confPsw) && HashService.check(psw, currentUser.getPassword())) {

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);

            if (commonService.updateUserProfile(currentUser)) {

                String page = createRedirectURL(req, CommandName.TO_PROFILE_PAGE);
                resolver = new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
                req.getSession().setAttribute(RequestConstant.SUCCESS,
                        MessageManager.INSTANCE.getMessage(MessageConstant.UPDATED_SUCCESS));
                logger.info("User profile info successfully changed");
            } else {
                req.setAttribute(RequestConstant.ERROR,
                        MessageManager.INSTANCE.getMessage(MessageConstant.UPDATE_ERROR));
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
