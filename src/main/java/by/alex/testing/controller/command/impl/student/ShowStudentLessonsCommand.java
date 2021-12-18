package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamFromReqHandler;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.LessonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public final class ShowStudentLessonsCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see StudentService
     */
    private final StudentService studentService;

    /**
     * @see LessonService
     */
    private final LessonService lessonService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowStudentLessonsCommand() {
        this.studentService = ServiceFactory.getInstance().getStudentService();
        this.lessonService = ServiceFactory.getInstance().getLessonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException {

        ViewResolver resolver = new ViewResolver(PageConstant.LESSONS_VIEW);
        User student = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        long studentId = student.getId();

        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);
        long courseId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.COURSE_ID);

        int count = lessonService.countStudentLessons(courseId, studentId);
        int start = this.definePagination(req, count, recordsPerPage);

        Map<Lesson, Attendance> lessons = studentService
                .findLessons(courseId, studentId, start, recordsPerPage);
        req.setAttribute(RequestConstant.LESSONS, lessons);
        return resolver;
    }
}
