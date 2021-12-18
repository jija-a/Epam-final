package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamFromReqHandler;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class UpdateCourseCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * @see CourseService
     */
    private final CourseService courseService;

    /**
     * Class constructor. Initializes service.
     */
    public UpdateCourseCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.teacherService = factory.getTeacherService();
        this.courseService = factory.getCourseService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        long courseId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.COURSE_ID);
        String courseName = req.getParameter(RequestConstant.COURSE_NAME);
        long categoryId = Long.parseLong(
                req.getParameter(RequestConstant.COURSE_CATEGORY_ID));

        Course course = courseService.findCourse(courseId);
        course.setName(courseName.trim());
        course.setCategory(CourseCategory.builder()
                .id(categoryId)
                .build());

        List<String> errors = teacherService.updateCourse(course, teacher);
        if (errors.isEmpty()) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.UPDATED_SUCCESS);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERRORS, errors);
        }
        return resolver;
    }
}
