package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;
import by.alex.testing.service.UserService;
import by.alex.testing.service.impl.CourseServiceImpl;
import by.alex.testing.service.impl.TestServiceImpl;
import by.alex.testing.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseUsers implements Command {

    private final CourseService service;

    public ShowCourseUsers() {
        this.service = new CourseServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        Course course = service.readCourseById(Long.parseLong(courseId));
        List<User> users = service.readUsersByCourseId(Long.parseLong(courseId));

        req.setAttribute(RequestConstant.COURSE, course);
        req.setAttribute(RequestConstant.USERS, users);
        return PageConstant.COURSE_USERS;
    }
}
