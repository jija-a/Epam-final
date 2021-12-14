package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.CommonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUsersCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowUsersCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final CommonService commonService;

    public ShowUsersCommand() {
        commonService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        List<User> users;

        int recordsPerPage = req.getParameter(RequestConstant.RECORDS_PER_PAGE) != null ?
                Integer.parseInt(req.getParameter(RequestConstant.RECORDS_PER_PAGE)) :
                DEFAULT_PAGINATION_LIMIT;
        logger.debug("Records per page - {}", recordsPerPage);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (!StringUtils.isNullOrEmpty(search)) {
            logger.debug("Search users request by '{}' received", search);
            int count = commonService.countAllUsers(search.trim());
            int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
            users = commonService.findUsersByName(start, recordsPerPage, search);
        } else {
            logger.debug("Search all users request received");
            int count = commonService.countAllUsers();
            int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
            users = commonService.findAllUsers(start, recordsPerPage);
        }

        req.setAttribute(RequestConstant.USERS, users);
        return new ViewResolver(PageConstant.USERS_LIST_PAGE);
    }
}
