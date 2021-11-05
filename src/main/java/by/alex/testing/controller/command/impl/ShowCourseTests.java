package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Quiz;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;
import by.alex.testing.service.impl.CourseServiceImpl;
import by.alex.testing.service.impl.TestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseTests implements Command {

    private final CourseService service;

    public ShowCourseTests() {
        this.service = new CourseServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        List<Quiz> tests = service.readAllTestsByCourseName(Long.parseLong(courseId));
        req.setAttribute(RequestConstant.TESTS, tests);
        return PageConstant.COURSE_TESTS;
    }
}
