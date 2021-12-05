package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.NotEnoughParametersException;
import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamsFromRequestHandler;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.*;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ShowStudentLessonsCommand implements Command {

    private final StudentService studentService;
    private final TeacherService teacherService;

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    public ShowStudentLessonsCommand() {
        studentService = ServiceFactory.getInstance().getStudentService();
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException {

        // TODO: 5.12.21 sortBy
        ViewResolver resolver = new ViewResolver(PageConstant.LESSONS_VIEW);
        User student = (User) req.getSession().getAttribute(RequestConstant.USER);
        long studentId = student.getId();

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);
        long courseId = ParamsFromRequestHandler.getLongParameter(req, RequestConstant.COURSE_ID);

        int count = studentService.countStudentLessons(courseId, studentId);
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);

        Map<Lesson, Attendance> lessons =
                studentService.findAllLessons(courseId, studentId, start, recordsPerPage);
        req.setAttribute(RequestConstant.LESSONS, lessons);
        return resolver;
    }
}
