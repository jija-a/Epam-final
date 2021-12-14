package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.BaseParameterValidator;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateLessonCommand implements Command {

    private final TeacherService teacherService;

    public CreateLessonCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        String page = createRedirectURL(req, CommandName.SHOW_LESSONS);
        ViewResolver resolver = new ViewResolver(page);

        List<String> errors = this.createLesson(req);
        if (errors.isEmpty()) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.CREATE_SUCCESS));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERROR, errors);
        }

        return resolver;
    }

    private List<String> createLesson(HttpServletRequest req)
            throws ServiceException, NotEnoughParametersException {

        Long courseId = (Long) req.getSession().getAttribute(RequestConstant.COURSE_ID);

        String title = req.getParameter(RequestConstant.LESSON_TITLE);
        String startDate = req.getParameter(RequestConstant.START_DATE);
        String endDate = req.getParameter(RequestConstant.END_DATE);

        if (BaseParameterValidator.isNullOrEmpty(title, startDate, endDate) || courseId == null) {
            throw new NotEnoughParametersException();
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

        return teacherService.createLesson(lesson);
    }

    private List<Attendance> createAttendances(Lesson lesson) throws ServiceException {
        List<User> users = teacherService.findCourseUsers(lesson.getCourseId());

        List<Attendance> attendances = new ArrayList<>();
        for (User user : users) {
            Attendance attendance = Attendance.builder()
                    .student(user)
                    .status(AttendanceStatus.ATTENDED)
                    .mark(null)
                    .build();
            attendances.add(attendance);
        }
        return attendances;
    }
}
