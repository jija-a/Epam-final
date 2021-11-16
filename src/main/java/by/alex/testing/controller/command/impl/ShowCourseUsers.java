package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseUsers implements Command {

    private final CourseUserService courseUserService;
    private final CourseService courseService;

    public ShowCourseUsers() {
        this.courseUserService =
                ServiceFactory.getInstance().getCourseUserService();
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        Course course = courseService.readCourseById(Long.parseLong(courseId));
        List<User> users = courseUserService.readUsersByCourseId(Long.parseLong(courseId));

        req.setAttribute(RequestConstant.COURSE, course);
        req.setAttribute(RequestConstant.USERS, users);
        return new ViewResolver(PageConstant.COURSE_USERS);
    }
}
