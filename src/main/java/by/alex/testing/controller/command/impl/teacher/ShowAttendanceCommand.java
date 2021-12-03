package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamsFromRequestHandler;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAttendanceCommand implements Command {

    private final TeacherService teacherService;

    public ShowAttendanceCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        ViewResolver resolver = new ViewResolver(PageConstant.ATTENDANCES_VIEW);
        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);

        Long lessonId = ParamsFromRequestHandler.getLessonIdParameter(req);

        try {
            List<Attendance> attendances = teacherService.findAllAttendances(lessonId, teacher);
            req.setAttribute(RequestConstant.ATTENDANCES, attendances);
            req.setAttribute(RequestConstant.STATUSES, AttendanceStatus.values());
            req.getSession().setAttribute(RequestConstant.LESSON_ID, lessonId);
        } catch (CourseAccessDeniedException e) {
            logger.info(e.getMessage());
            String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
            resolver.setView(page);
        }
        return resolver;
    }
}
