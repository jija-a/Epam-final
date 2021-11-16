package by.alex.testing.controller.command.impl.direct;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ToUpdateCourseInfoPageCommand implements Command {

    private final CourseService courseService;

    public ToUpdateCourseInfoPageCommand() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        Course course = courseService.readCourseById(Long.parseLong(courseId));
        List<CourseCategory> categories = courseService.readAllCourseCategories();

        req.setAttribute(RequestConstant.COURSE, course);
        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        return new ViewResolver(PageConstant.UPDATE_COURSE);
    }
}
