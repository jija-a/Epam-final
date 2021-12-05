package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseRequestsCommand implements Command {

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final TeacherService teacherService;

    public ShowCourseRequestsCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long teacherId = user.getId();

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);

        int count = teacherService.countAllRequests(teacherId);
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);

        List<CourseUser> courseUsers =
                teacherService.findRequestsOnCourse(start, recordsPerPage, teacherId);
        req.setAttribute(RequestConstant.USERS, courseUsers);

        return new ViewResolver(PageConstant.COURSE_REQUESTS);
    }
}
