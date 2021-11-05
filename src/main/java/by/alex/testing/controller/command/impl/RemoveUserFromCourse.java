package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.impl.CourseServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveUserFromCourse implements Command {

    private final CourseService service;

    public RemoveUserFromCourse() {
        this.service = new CourseServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServiceException {

        long courseId = Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));
        long userId = Long.parseLong(req.getParameter(RequestConstant.USER_ID));

        service.removeUserFromCourse(userId, courseId);
        return PageConstant.HOME_PAGE;
    }
}
