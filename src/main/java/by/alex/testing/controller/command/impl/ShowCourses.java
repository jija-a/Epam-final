package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourses implements Command {

    private final CourseService courseService;

    public ShowCourses() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String courseTitle = req.getParameter(RequestConstant.COURSE_TITLE);

        List<Course> courses = courseService.readCourseByTitle(courseTitle);
        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES);
    }
}
