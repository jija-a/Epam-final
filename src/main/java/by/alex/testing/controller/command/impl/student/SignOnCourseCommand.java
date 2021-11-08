package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.impl.CourseUserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOnCourseCommand implements Command {

    private final CourseUserService service;

    public SignOnCourseCommand() {
        this.service = new CourseUserServiceImpl();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long userId = user.getId();
        long courseId = Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));

        service.addUserOnCourse(userId, courseId);

        return new ViewResolver(PageConstant.HOME_PAGE);
    }
}
