package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdateCourseCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(UpdateCourseCommand.class);

    private final TeacherService teacherService;

    public UpdateCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Create course command received");
        Course course = TeacherActionResolver.resolvePermission(req);
        if (course == null) {
            return new ShowTeacherCoursesCommand().execute(req, resp);
        }
        req.setAttribute(RequestConstant.COURSE, course);
        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        String courseName = req.getParameter(RequestConstant.COURSE_NAME);
        long categoryId = Long.parseLong(req.getParameter(RequestConstant.COURSE_CATEGORY_ID));

        logger.debug("Course name: {}", courseName);

        course.setName(courseName);
        course.setCategory(CourseCategory.builder().id(categoryId).build());

        List<String> errors = teacherService.updateCourse(course);
        if (errors.isEmpty()) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.UPDATED_SUCCESS));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERRORS, errors);
        }

        return resolver;
    }

}
