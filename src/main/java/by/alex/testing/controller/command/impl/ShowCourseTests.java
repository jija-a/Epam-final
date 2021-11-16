package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseTests implements Command {

    private final CourseService courseService;

    public ShowCourseTests() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        Course course = courseService.readCourseById(Long.parseLong(courseId));
        List<Quiz> tests = courseService.readAllTestsByCourseName(Long.parseLong(courseId));

        req.setAttribute(RequestConstant.COURSE, course);
        req.setAttribute(RequestConstant.TESTS, tests);
        return new ViewResolver(PageConstant.COURSE_TESTS);
    }
}
