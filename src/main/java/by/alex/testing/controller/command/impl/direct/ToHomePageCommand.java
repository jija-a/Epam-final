package by.alex.testing.controller.command.impl.direct;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToHomePageCommand implements Command {

    private static final int HOME_PAGE_TESTS_LIMIT = 10;
    private final TestService testService;
    private final CourseService courseService;

    public ToHomePageCommand() {
        this.testService = ServiceFactory.getInstance().getTestService();
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        if (req.getSession(false) != null) {
            User user = (User) req.getSession().getAttribute(RequestConstant.USER);
            if (user != null) {
                Map<Quiz, String> testCourseNameMap = new HashMap<>();
                long userId = user.getId();
                List<Quiz> tests = testService.showUserNearestTests(userId, HOME_PAGE_TESTS_LIMIT);
                for (Quiz test : tests) {
                    Course course = courseService.readCourseById(test.getCourseId());
                    testCourseNameMap.put(test, course.getName());
                }
                req.setAttribute(RequestConstant.TESTS, testCourseNameMap);
            }
        }
        return new ViewResolver(PageConstant.HOME_PAGE);
    }
}
