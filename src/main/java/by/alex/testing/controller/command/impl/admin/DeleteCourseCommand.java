package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.impl.teacher.ShowTeacherCoursesCommand;
import by.alex.testing.controller.command.impl.teacher.TeacherActionResolver;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCourseCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteCourseCommand.class);

    private final CourseService courseService;

    public DeleteCourseCommand() {
        courseService = ServiceFactory.getInstance().getCourseService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete course command received");
        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        UserRole role = user.getRole();

        if (role.equals(UserRole.TEACHER)) {
            Course course = TeacherActionResolver.resolvePermission(req);
            if (course == null) {
                return new ShowTeacherCoursesCommand().execute(req, resp);
            }
        }

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        if (!StringUtils.isNullOrEmpty(courseId)) {
            courseService.deleteCourse(Long.parseLong(courseId));
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));
        }

        String commandName = role.equals(UserRole.TEACHER) ?
                CommandName.SHOW_TEACHER_COURSES : CommandName.SHOW_COURSES;

        String page = createRedirectURL(req, commandName);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
