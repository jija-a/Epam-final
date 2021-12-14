package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamsFromRequestHandler;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdateCourseCommand implements Command {

    private final TeacherService teacherService;
    private final CommonService commonService;

    public UpdateCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
        commonService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException {

        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);
        long courseId = ParamsFromRequestHandler.getLongParameter(req, RequestConstant.COURSE_ID);
        String courseName = req.getParameter(RequestConstant.COURSE_NAME);
        long categoryId = Long.parseLong(req.getParameter(RequestConstant.COURSE_CATEGORY_ID));

        Course course = commonService.readCourseById(courseId);
        course.setName(courseName.trim());
        course.setCategory(CourseCategory.builder()
                .id(categoryId)
                .build());

        List<String> errors = teacherService.updateCourse(course, teacher);
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
