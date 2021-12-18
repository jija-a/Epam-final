package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.ParameterValidator;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.LessonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class CreateLessonCommand implements Command {

    /**
     * @see LessonService
     */
    private final LessonService lessonService;

    /**
     * @see CourseUserService
     */
    private final CourseUserService courseUserService;

    /**
     * Class constructor. Initializes service.
     */
    public CreateLessonCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.lessonService = factory.getLessonService();
        this.courseUserService = factory.getCourseUserService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException {

        String page = createRedirectURL(req, CommandName.SHOW_LESSONS);
        ViewResolver resolver = new ViewResolver(page);

        List<String> errors = this.createLesson(req);
        if (errors.isEmpty()) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.CREATE_SUCCESS);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERROR, errors);
        }

        return resolver;
    }

    private List<String> createLesson(final HttpServletRequest req)
            throws ServiceException, ParametersException {

        Long courseId =
                (Long) req.getSession().getAttribute(RequestConstant.COURSE_ID);

        String title = req.getParameter(RequestConstant.LESSON_TITLE);
        String startDate = req.getParameter(RequestConstant.START_DATE);
        String endDate = req.getParameter(RequestConstant.END_DATE);

        if (ParameterValidator.isNullOrEmpty(title, startDate, endDate)
                || courseId == null) {
            throw new ParametersException();
        }
        title = title.trim();

        Lesson lesson = Lesson.builder()
                .title(title)
                .startDate(LocalDateTime.parse(startDate))
                .endDate(LocalDateTime.parse(endDate))
                .courseId(courseId)
                .build();

        List<Attendance> attendances = this.createAttendances(lesson);
        lesson.setAttendances(attendances);

        return lessonService.createLesson(lesson);
    }

    private List<Attendance> createAttendances(final Lesson lesson)
            throws ServiceException {

        List<User> users =
                courseUserService.findCourseUsers(lesson.getCourseId());

        List<Attendance> attendances = new ArrayList<>();
        for (User user : users) {
            Attendance attendance = Attendance.builder()
                    .student(user)
                    .status(AttendanceStatus.PRESENT)
                    .mark(null)
                    .build();
            attendances.add(attendance);
        }
        return attendances;
    }
}
