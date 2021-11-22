package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowUsersCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowUsersCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final UserService userService;

    public ShowUsersCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("To users list page command received");
        List<User> users = new ArrayList<>();

        String stringRec = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recOnPage = stringRec == null ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(stringRec);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recOnPage);

        String search = req.getParameter(RequestConstant.SEARCH);
        String reqPage = req.getParameter(RequestConstant.PAGE);

        int page = reqPage != null ? Integer.parseInt(reqPage) : 1;

        if (!StringUtils.isNullOrEmpty(search)) {
            logger.debug("Search users request by '{}' received", search);
            users = this.searchByName(search, page, req, recOnPage);
        }
        if (users.isEmpty()) {
            logger.debug("Search all users request received");
            users = this.searchAll(page, req, recOnPage);
        }

        req.setAttribute(RequestConstant.USERS, users);
        return new ViewResolver(PageConstant.USERS_LIST_PAGE);
    }

    private List<User> searchByName(String search, int page, HttpServletRequest req, int recOnPage)
            throws ServiceException {

        search = search.trim();
        req.setAttribute(RequestConstant.SEARCH, search);
        Integer count = userService.countAllUsers(search);
        int start = this.definePagination(req, count, page, recOnPage);
        return userService.findUsersByName(start, recOnPage, search);
    }

    private List<User> searchAll(int page, HttpServletRequest req, int recOnPage)
            throws ServiceException {

        Integer count = userService.countAllUsers();
        int start = this.definePagination(req, count, page, recOnPage);
        return userService.findAllUsers(start, recOnPage);
    }
}
