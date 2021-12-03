package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.BaseParameterValidator;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCourseCommand implements Command {

    private final TeacherService teacherService;

    public CreateCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long teacherId = user.getId();
        String courseName = req.getParameter(RequestConstant.COURSE_NAME);
        long categoryId = Long.parseLong(req.getParameter(RequestConstant.COURSE_CATEGORY_ID));

        if (StringUtils.isNullOrEmpty(courseName)) {
            throw new NotEnoughParametersException("Not enough parameters");
        }

        Course course = Course.builder()
                .name(courseName.trim())
                .owner(User.builder().id(teacherId).build())
                .category(CourseCategory.builder().id(categoryId).build())
                .build();

        List<String> errors = teacherService.createCourse(course);
        if (errors.isEmpty()) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.CREATE_SUCCESS));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERRORS, errors);
        }

        return resolver;
    }
}
