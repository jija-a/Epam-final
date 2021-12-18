package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowAvailableCoursesCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see StudentService
     */
    private final StudentService service;

    /**
     * Class constructor. Initializes service.
     */
    public ShowAvailableCoursesCommand() {
        this.service = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException {

        List<Course> courses;
        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long studentId = user.getId();

        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (!StringUtils.isNullOrEmpty(search)) {
            req.setAttribute(RequestConstant.SEARCH, search.trim());
            courses = this.findByRequest(req, recPerPage, search, studentId);
        } else {
            courses = this.findAll(req, recPerPage, studentId);
        }

        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }

    private List<Course> findAll(final HttpServletRequest req,
                                 final int recordsPerPage,
                                 final long studentId)
            throws ServiceException {

        int count = service.countAvailableCourses(studentId);
        int start = this.definePagination(req, count, recordsPerPage);
        return service.readAvailableCourses(studentId, start, recordsPerPage);
    }

    private List<Course> findByRequest(final HttpServletRequest req,
                                       final int recPerPage,
                                       final String search,
                                       final long studentId)
            throws ServiceException {

        int count = service.countAvailableCourses(studentId, search.trim());
        int start = this.definePagination(req, count, recPerPage);
        return service.readAvailableCourses(studentId, start,
                recPerPage, search.trim());
    }
}
