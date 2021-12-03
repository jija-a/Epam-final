package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamsFromRequestHandler;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
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

    private final TeacherService teacherService;

    public ShowCourseUsersCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        List<CourseUser> users = new ArrayList<>();
        long courseId = ParamsFromRequestHandler.getCourseIdParameter(req);
        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        req.getSession().setAttribute(RequestConstant.COURSE_ID, courseId);

        try {
            if (!StringUtils.isNullOrEmpty(search)) {
                users = this.findBySearchRequest(req, courseId, recordsPerPage, search, teacher);
            }
            if (users.isEmpty()) {
                users = this.findAll(req, courseId, recordsPerPage, teacher);
            }
        } catch (CourseAccessDeniedException e) {
            logger.info(e.getMessage());
            String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
            return new ViewResolver(page);
        }

        req.setAttribute(RequestConstant.COURSE_USERS, users);
        return new ViewResolver(PageConstant.COURSE_USERS_PAGE);
    }

    private List<CourseUser> findBySearchRequest(HttpServletRequest req, long courseId,
                                                 int recordsPerPage, String search, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        logger.debug("Search users request by '{}' received", search);
        req.setAttribute(RequestConstant.SEARCH, search);
        int count = teacherService.countAllCourseUsers(courseId, search);
        int start = this.definePagination(req, count, recordsPerPage);
        return teacherService.findCourseUsersByName(start, recordsPerPage, courseId, search, teacher);
    }

    private List<CourseUser> findAll(HttpServletRequest req, long courseId, int recordsPerPage, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        logger.debug("Search all users request received");
        int count = teacherService.countAllCourseUsers(courseId);
        int start = this.definePagination(req, count, recordsPerPage);
        return teacherService.findCourseUsers(start, recordsPerPage, courseId, teacher);
    }
}