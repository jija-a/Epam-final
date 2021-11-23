package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateTestInfoCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(UpdateTestInfoCommand.class);

    private final TestService testService;

    public UpdateTestInfoCommand() {
        testService = ServiceFactory.getInstance().getTestService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Update test command received");
        Course course = TeacherActionResolver.resolvePermission(req);
        if (course == null) {
            return new ShowTeacherCoursesCommand().execute(req, resp);
        }
        req.getSession().setAttribute(RequestConstant.COURSE_ID, course.getId());
        String page = createRedirectURL(req, CommandName.SHOW_TESTS);
        ViewResolver resolver = new ViewResolver(page);

        long testId = Long.parseLong(req.getParameter(RequestConstant.TEST_ID));
        String title = req.getParameter(RequestConstant.TEST_TITLE);
        int attempts = Integer.parseInt(req.getParameter(RequestConstant.TEST_ATTEMPTS));
        LocalDateTime startDate = LocalDateTime.parse(req.getParameter(RequestConstant.TEST_START_DATE));
        LocalDateTime endDate = LocalDateTime.parse(req.getParameter(RequestConstant.TEST_END_DATE));
        int timeToAnswer = Integer.parseInt(req.getParameter(RequestConstant.TEST_TIME_TO_ANSWER));
        int maxScore = Integer.parseInt(req.getParameter(RequestConstant.TEST_MAX_SCORE));

        Quiz test = testService.readTestById(testId);
        test.setTitle(title);
        test.setAttempts(attempts);
        test.setStartDate(startDate);
        test.setEndDate(endDate);
        test.setTimeToAnswer(timeToAnswer);
        test.setMaxScore(maxScore);

        List<String> errors = testService.updateTest(test);
        if (errors.isEmpty()) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.UPDATED_SUCCESS));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERRORS, errors);
        }

        return resolver;
    }

}
