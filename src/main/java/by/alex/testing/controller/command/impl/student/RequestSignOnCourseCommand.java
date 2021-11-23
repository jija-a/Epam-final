package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestSignOnCourseCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(RequestSignOnCourseCommand.class);

    private final CourseUserService courseUserService;

    public RequestSignOnCourseCommand() {
        this.courseUserService = ServiceFactory.getInstance().getCourseUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Request sign on course command received");

        long courseId = Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));
        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long studentId = user.getId();

        CourseUser courseUser = CourseUser.builder()
                .user(User.builder().id(studentId).build())
                .course(Course.builder().id(courseId).build())
                .status(UserCourseStatus.REQUESTED)
                .build();

        courseUserService.create(courseUser);
        req.getSession().setAttribute(RequestConstant.SUCCESS,
                MessageManager.INSTANCE.getMessage(MessageConstant.SIGNED));

        String page = createRedirectURL(req, CommandName.SHOW_COURSES);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
