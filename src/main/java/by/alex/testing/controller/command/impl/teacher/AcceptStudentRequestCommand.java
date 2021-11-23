package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptStudentRequestCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(AcceptStudentRequestCommand.class);

    private final CourseUserService courseUserService;

    public AcceptStudentRequestCommand() {
        this.courseUserService = ServiceFactory.getInstance().getCourseUserService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        long courseId = Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));
        long userId = Long.parseLong(req.getParameter(RequestConstant.USER_ID));

        CourseUser courseUser = courseUserService.findCourseUser(courseId, userId);
        courseUser.setStatus(UserCourseStatus.ON_COURSE);
        courseUserService.update(courseUser);
        logger.debug("Course user: {}", courseUser);

        req.getSession().setAttribute(RequestConstant.SUCCESS,
                MessageManager.INSTANCE.getMessage(MessageConstant.ACCEPT_SUCCESS));
        String page = createRedirectURL(req, CommandName.SHOW_COURSE_REQUESTS);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}