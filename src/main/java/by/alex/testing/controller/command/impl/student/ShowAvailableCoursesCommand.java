package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAvailableCoursesCommand implements Command {

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final StudentService service;

    public ShowAvailableCoursesCommand() {
        this.service = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException {

        List<Course> courses;
        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long studentId = user.getId();

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (!StringUtils.isNullOrEmpty(search)) {
            req.setAttribute(RequestConstant.SEARCH, search.trim());
            courses = this.findBySearchRequest(req, recordsPerPage, search, studentId);
        } else {
            courses = this.findAll(req, recordsPerPage, studentId);
        }

        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }

    private List<Course> findAll(HttpServletRequest req, int recordsPerPage, long studentId)
            throws ServiceException {

        int count = service.countAvailableCourses(studentId);
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
        return service.readAvailableCourses(studentId, start, recordsPerPage);
    }

    private List<Course> findBySearchRequest(HttpServletRequest req, int recordsPerPage,
                                             String search, long studentId) throws ServiceException {

        int count = service.countAvailableCourses(studentId, search.trim());
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
        return service.readAvailableCourses(studentId, start, recordsPerPage, search.trim());
    }
}
