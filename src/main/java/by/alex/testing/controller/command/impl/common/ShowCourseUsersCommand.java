package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.impl.teacher.ShowTeacherCoursesCommand;
import by.alex.testing.controller.command.impl.teacher.TeacherActionResolver;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowCourseUsersCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowCourseUsersCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final CourseUserService courseUserService;

    public ShowCourseUsersCommand() {
        courseUserService = ServiceFactory.getInstance().getCourseUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("Show course users page command received");
        Course course = TeacherActionResolver.resolvePermission(req);
        if (course == null) {
            return new ShowTeacherCoursesCommand().execute(req, resp);
        }
        req.setAttribute(RequestConstant.COURSE, course);

        List<User> users = new ArrayList<>();
        String stringRec = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recOnPage = stringRec == null ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(stringRec);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recOnPage);

        String search = req.getParameter(RequestConstant.SEARCH);
        String reqPage = req.getParameter(RequestConstant.PAGE);

        int page = reqPage != null ? Integer.parseInt(reqPage) : 1;

        if (!StringUtils.isNullOrEmpty(search)) {
            logger.debug("Search users request by '{}' received", search);
            users = this.searchByName(search, page, req, recOnPage, course.getId());
        }
        if (users.isEmpty()) {
            logger.debug("Search all users request received");
            users = this.searchAll(page, req, recOnPage, course.getId());
        }

        req.setAttribute(RequestConstant.USERS, users);
        return new ViewResolver(PageConstant.COURSE_USERS_PAGE);
    }

    private List<User> searchByName(String search, int page, HttpServletRequest req, int recOnPage, long courseId)
            throws ServiceException {

        search = search.trim();
        req.setAttribute(RequestConstant.SEARCH, search);
        Integer count = courseUserService.countAllCourseUsers(courseId, search);
        int start = this.definePagination(req, count, page, recOnPage);
        return courseUserService.findCourseUsersByName(start, recOnPage, courseId, search);
    }

    private List<User> searchAll(int page, HttpServletRequest req, int recOnPage, long courseId)
            throws ServiceException {

        Integer count = courseUserService.countAllCourseUsers(courseId);
        int start = this.definePagination(req, count, page, recOnPage);
        return courseUserService.findCourseUsers(start, recOnPage, courseId);
    }
}
