package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.AdminService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteUserCommand implements Command {

    /**
     * @see UserService
     */
    private final UserService userService;

    /**
     * @see AdminService
     */
    private final AdminService adminService;

    /**
     * Class constructor. Initializes service.
     */
    public DeleteUserCommand() {
        this.userService = ServiceFactory.getInstance().getCommonService();
        this.adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        String userId = req.getParameter(RequestConstant.USER_ID);
        if (!StringUtils.isNullOrEmpty(userId)) {
            long id = Long.parseLong(userId);
            User user = userService.findUser(id);
            if (user.getRole().equals(UserRole.ADMIN)) {
                String msg = MessageManager.INSTANCE
                        .getMessage(MessageConstant.CANT_DELETE_ADMIN);
                req.setAttribute(RequestConstant.ERROR, msg);
                return new ShowUsersCommand().execute(req, resp);
            }
            adminService.deleteUser(id);
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.DELETED);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
        }

        String page = createRedirectURL(req, CommandName.SHOW_USERS);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
