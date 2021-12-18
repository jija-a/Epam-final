package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowUsersCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see UserService
     */
    private final UserService userService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowUsersCommand() {
        userService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        List<User> users;
        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (StringUtils.isNullOrEmpty(search)) {
            users = this.findAll(req, recordsPerPage);
        } else {
            users = this.findByRequest(req, recordsPerPage, search);
        }

        req.setAttribute(RequestConstant.USERS, users);
        return new ViewResolver(PageConstant.USERS_LIST_PAGE);
    }

    private List<User> findAll(final HttpServletRequest req,
                               final int recordsPerPage)
            throws ServiceException {

        int count = userService.countAllUsers();
        int start = this.definePagination(req, count, recordsPerPage);
        return userService.findAllUsers(start, recordsPerPage);
    }

    private List<User> findByRequest(final HttpServletRequest req,
                                     final int recordsPerPage,
                                     final String search)
            throws ServiceException {

        req.setAttribute(RequestConstant.SEARCH, search.trim());
        int count = userService.countAllUsers(search.trim());
        int start = this.definePagination(req, count, recordsPerPage);
        return userService.findAllUsers(start, recordsPerPage, search.trim());
    }
}
