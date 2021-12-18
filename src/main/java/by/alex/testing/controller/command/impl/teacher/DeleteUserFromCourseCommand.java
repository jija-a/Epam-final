package by.alex.testing.controller.command.impl.teacher;

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
import by.alex.testing.service.AccessException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteUserFromCourseCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * Class constructor. Initializes service.
     */
    public DeleteUserFromCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        String page = createRedirectURL(req, CommandName.SHOW_COURSE_USERS);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        Long courseId = (Long) req.getSession()
                .getAttribute(RequestConstant.COURSE_ID);
        String userIdParam = req.getParameter(RequestConstant.USER_ID);

        if (StringUtils.isNullOrEmpty(userIdParam) || courseId == null) {
            throw new ParametersException("Params are missing");
        }
        long userId = Long.parseLong(userIdParam);

        if (this.removeUser(courseId, userId, teacher)) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.DELETED);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.DELETE_ERROR);
            req.setAttribute(RequestConstant.ERROR, msg);
        }
        return resolver;
    }

    private boolean removeUser(final Long courseId,
                               final long userId,
                               final User teacher)
            throws ServiceException, AccessException {

        CourseUser courseUser = CourseUser.builder()
                .user(User.builder().id(userId).build())
                .course(Course.builder().id(courseId).build())
                .status(UserCourseStatus.ON_COURSE)
                .build();
        return teacherService.deleteUserFromCourse(courseUser, teacher);
    }
}
