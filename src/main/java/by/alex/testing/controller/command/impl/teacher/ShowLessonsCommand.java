package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamFromReqHandler;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.LessonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowLessonsCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * @see LessonService
     */
    private final LessonService lessonService;


    /**
     * Class constructor. Initializes service.
     */
    public ShowLessonsCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
        this.lessonService = ServiceFactory.getInstance().getLessonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        ViewResolver resolver = new ViewResolver(PageConstant.LESSONS_VIEW);

        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);
        long courseId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.COURSE_ID);

        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        int count = lessonService.countAllLessons(courseId);
        int start = this.definePagination(req, count, recordsPerPage);

        List<Lesson> lessons = teacherService
                .findAllLessons(courseId, start, recordsPerPage, teacher);
        req.setAttribute(RequestConstant.LESSONS, lessons);
        return resolver;
    }
}
