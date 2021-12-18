package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.ParameterValidator;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.UnknownEntityException;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class CreateCourseCommand implements Command {

    /**
     * @see CourseService
     */
    private final CourseService courseService;

    /**
     * Class constructor. Initializes service.
     */
    public CreateCourseCommand() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException {

        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long teacherId = user.getId();
        String courseName = req.getParameter(RequestConstant.COURSE_NAME);
        String categoryId =
                req.getParameter(RequestConstant.COURSE_CATEGORY_ID);

        if (ParameterValidator.isNullOrEmpty(courseName, categoryId)) {
            throw new ParametersException("Not enough parameters");
        }
        long id = Long.parseLong(categoryId);
        courseName = courseName.trim();

        Course course = Course.builder()
                .name(courseName.trim())
                .owner(User.builder().id(teacherId).build())
                .category(CourseCategory.builder().id(id).build())
                .build();

        try {
            List<String> errors = courseService.createCourse(course);
            if (errors.isEmpty()) {
                String msg = MessageManager.INSTANCE
                        .getMessage(MessageConstant.CREATE_SUCCESS);
                req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERRORS, errors);
            }
        } catch (UnknownEntityException e) {
            return resolver;
        }

        return resolver;
    }
}
