package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class LeaveCourseCommand implements Command {

    /**
     * @see StudentService
     */
    private final StudentService studentService;

    /**
     * Class constructor. Initializes service.
     */
    public LeaveCourseCommand() {
        this.studentService = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException {

        String page = createRedirectURL(req, CommandName.SHOW_STUDENT_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        User student = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        long courseId =
                Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));

        CourseUser courseUser = CourseUser.builder()
                .user(student)
                .course(Course.builder().id(courseId).build())
                .status(UserCourseStatus.ON_COURSE)
                .build();

        if (studentService.leaveCourse(courseUser)) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.UNSIGNED);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.CANT_UNSIGN);
            req.setAttribute(RequestConstant.ERROR, msg);
        }
        return resolver;
    }
}
