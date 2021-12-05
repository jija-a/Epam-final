package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.NotEnoughParametersException;
import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowStudentCoursesCommand implements Command {

    private final StudentService service;

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    public ShowStudentCoursesCommand() {
        this.service = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long studentId = user.getId();

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);

        int count = service.countStudentCourses(studentId);
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);

        List<CourseUser> courses = service.readStudentCourses(studentId, start, recordsPerPage);
        req.setAttribute(RequestConstant.COURSE_USER, courses);
        return new ViewResolver(PageConstant.STUDENT_COURSES_PAGE);
    }
}
