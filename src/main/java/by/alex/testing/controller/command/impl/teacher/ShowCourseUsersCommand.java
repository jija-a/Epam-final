package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamFromReqHandler;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowCourseUsersCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * @see CourseUserService
     */
    private final CourseUserService courseUserService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowCourseUsersCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.teacherService = factory.getTeacherService();
        this.courseUserService = factory.getCourseUserService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        List<CourseUser> users;
        long courseId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.COURSE_ID);
        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);

        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        req.getSession().setAttribute(RequestConstant.COURSE_ID, courseId);

        if (!StringUtils.isNullOrEmpty(search)) {
            users = this.findByRequest(req, courseId,
                    recPerPage, search, teacher);
        } else {
            users = this.findAll(req, courseId, recPerPage, teacher);
        }
        req.setAttribute(RequestConstant.COURSE_USERS, users);
        return new ViewResolver(PageConstant.COURSE_USERS_PAGE);
    }

    private List<CourseUser> findByRequest(final HttpServletRequest req,
                                           final long courseId,
                                           final int recordsPerPage,
                                           final String search,
                                           final User teacher)
            throws ServiceException {

        req.setAttribute(RequestConstant.SEARCH, search);
        int count = courseUserService.countAllCourseUsers(courseId, search);
        int start = this.definePagination(req, count, recordsPerPage);
        return courseUserService
                .findCourseUsersByName(start, recordsPerPage, courseId,
                        search, teacher);
    }

    private List<CourseUser> findAll(final HttpServletRequest req,
                                     final long courseId,
                                     final int recordsPerPage,
                                     final User teacher)
            throws ServiceException, AccessException {

        int count = courseUserService.countAllCourseUsers(courseId);
        int start = this.definePagination(req, count, recordsPerPage);
        return teacherService
                .findCourseUsers(start, recordsPerPage, courseId, teacher);
    }
}
