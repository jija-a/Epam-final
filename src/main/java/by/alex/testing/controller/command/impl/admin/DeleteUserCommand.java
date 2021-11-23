package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.impl.common.ShowUsersCommand;
import by.alex.testing.dao.DaoException;
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

public class DeleteUserCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteUserCommand.class);

    private final UserService userService;

    public DeleteUserCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete course command received");

        String userId = req.getParameter(RequestConstant.USER_ID);

        if (!StringUtils.isNullOrEmpty(userId)) {
            long id = Long.parseLong(userId);
            User user = userService.findUserById(id);
            if (user.getRole().equals(UserRole.ADMIN)) {
                req.setAttribute(RequestConstant.ERROR,
                        MessageManager.INSTANCE.getMessage(MessageConstant.CANT_DELETE_ADMIN));
                return new ShowUsersCommand().execute(req, resp);
            }
            userService.deleteUser(id);
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));
        }

        String page = createRedirectURL(req, CommandName.SHOW_USERS);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
