package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TestService;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTestCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteTestCommand.class);

    private final TestService testService;

    public DeleteTestCommand() {
        testService = ServiceFactory.getInstance().getTestService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete test from course command received");
        Course course = TeacherActionResolver.resolvePermission(req);
        if (course == null) {
            return new ShowTeacherCoursesCommand().execute(req, resp);
        }
        req.getSession().setAttribute(RequestConstant.COURSE_ID, course.getId());

        long testId = Long.parseLong(req.getParameter(RequestConstant.TEST_ID));

        testService.deleteTest(testId);
        req.getSession().setAttribute(RequestConstant.SUCCESS,
                MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));

        String page = createRedirectURL(req, CommandName.SHOW_TESTS);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
