package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseRequestsCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowCourseRequestsCommand.class);

    private final CourseUserService courseUserService;

    public ShowCourseRequestsCommand() {
        courseUserService = ServiceFactory.getInstance().getCourseUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Show course request command received");

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long userId = user.getId();

        List<CourseUser> courseUsers = courseUserService.findRequestsOnCourse(userId);
        logger.debug("Course users requested: {}", courseUsers);

        req.setAttribute(RequestConstant.USERS, courseUsers);
        logger.debug("Course requests: {}", courseUsers);
        return new ViewResolver(PageConstant.COURSE_REQUESTS);
    }
}
