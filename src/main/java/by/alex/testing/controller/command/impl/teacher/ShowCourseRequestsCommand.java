package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowCourseRequestsCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see CourseService
     */
    private final CourseService courseService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowCourseRequestsCommand() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long teacherId = user.getId();

        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        int count = courseService.countAllRequests(teacherId);
        int start = this.definePagination(req, count, recordsPerPage);

        List<CourseUser> courseUsers = courseService
                .findRequestsOnCourse(start, recordsPerPage, teacherId);
        req.setAttribute(RequestConstant.USERS, courseUsers);

        return new ViewResolver(PageConstant.COURSE_REQUESTS);
    }
}
