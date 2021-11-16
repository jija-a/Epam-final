package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserCoursesCommand implements Command {

    private final CourseService courseService;

    public ShowUserCoursesCommand() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        Long userId = user.getId();
        List<Course> courses = courseService.readUserCourses(userId);

        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.USER_COURSES);
    }
}
