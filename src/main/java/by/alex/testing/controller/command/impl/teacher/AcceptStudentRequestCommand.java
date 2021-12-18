package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.ParameterValidator;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AcceptStudentRequestCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * @see CourseUserService
     */
    private final CourseUserService courseUserService;

    /**
     * Class constructor. Initializes service.
     */
    public AcceptStudentRequestCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.teacherService = factory.getTeacherService();
        this.courseUserService = factory.getCourseUserService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        String view = createRedirectURL(req, CommandName.SHOW_COURSE_REQUESTS);
        ViewResolver resolver = new ViewResolver(view);

        String courseIdParam = req.getParameter(RequestConstant.COURSE_ID);
        String studentIdParam = req.getParameter(RequestConstant.USER_ID);

        if (ParameterValidator
                .isNullOrEmpty(courseIdParam, studentIdParam)) {
            throw new ParametersException();
        }
        long courseId = Long.parseLong(courseIdParam);
        long studentId = Long.parseLong(studentIdParam);
        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);

        if (this.acceptStudent(courseId, studentId, teacher)) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.ACCEPT_SUCCESS);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.CANT_ACCEPT);
            req.setAttribute(RequestConstant.ERROR, msg);
        }

        return resolver;
    }

    private boolean acceptStudent(final long courseId,
                                  final long studentId,
                                  final User teacher)
            throws ServiceException, AccessException {

        CourseUser courseUser =
                courseUserService.findCourseUser(courseId, studentId);
        if (courseUser == null) {
            return false;
        }
        courseUser.setStatus(UserCourseStatus.ON_COURSE);
        return teacherService.updateCourseUser(courseUser, teacher);
    }
}
