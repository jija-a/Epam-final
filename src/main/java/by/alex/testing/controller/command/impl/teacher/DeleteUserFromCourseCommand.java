package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserFromCourseCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteUserFromCourseCommand.class);

    private final TeacherService teacherService;

    public DeleteUserFromCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete user from course command received");
        Course course = TeacherActionResolver.resolvePermission(req);
        if (course == null) {
            return new ShowTeacherCoursesCommand().execute(req, resp);
        }
        req.getSession().setAttribute(RequestConstant.COURSE_ID, course.getId());

        String page = createRedirectURL(req, CommandName.SHOW_COURSE_USERS);
        ViewResolver resolver = new ViewResolver(page);

        String userId = req.getParameter(RequestConstant.USER_ID);

        if (!StringUtils.isNullOrEmpty(userId)) {
            CourseUser courseUser = CourseUser.builder()
                    .user(User.builder().id(Long.valueOf(userId)).build())
                    .course(Course.builder().id(course.getId()).build())
                    .status(UserCourseStatus.ON_COURSE)
                    .build();
            teacherService.deleteUserFromCourse(courseUser);
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        }

        return resolver;
    }
}
