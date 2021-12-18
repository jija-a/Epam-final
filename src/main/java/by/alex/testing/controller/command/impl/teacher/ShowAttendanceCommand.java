package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamFromReqHandler;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowAttendanceCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowAttendanceCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        ViewResolver resolver = new ViewResolver(PageConstant.ATTENDANCES_VIEW);
        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);

        long courseId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.COURSE_ID);
        Long lessonId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.LESSON_ID);

        List<Attendance> attendances =
                teacherService.findAllAttendances(lessonId, courseId, teacher);
        req.setAttribute(RequestConstant.ATTENDANCES, attendances);
        req.setAttribute(RequestConstant.STATUSES, AttendanceStatus.values());
        req.getSession().setAttribute(RequestConstant.LESSON_ID, lessonId);
        return resolver;
    }
}
