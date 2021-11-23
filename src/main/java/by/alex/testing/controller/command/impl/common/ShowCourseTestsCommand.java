package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.impl.teacher.ShowTeacherCoursesCommand;
import by.alex.testing.controller.command.impl.teacher.TeacherActionResolver;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseTestsCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowCourseTestsCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final TestService courseUserService;
    private final CourseService courseService;

    public ShowCourseTestsCommand() {
        courseUserService = ServiceFactory.getInstance().getTestService();
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("Show course tests page command received");
        Course course = TeacherActionResolver.resolvePermission(req);
        if (course == null) {
            return new ShowTeacherCoursesCommand().execute(req, resp);
        }
        req.setAttribute(RequestConstant.COURSE, course);

        String stringRec = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recOnPage = stringRec == null ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(stringRec);
        req.setAttribute(RequestConstant.COURSE_ID, course.getId());
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recOnPage);

        String reqPage = req.getParameter(RequestConstant.PAGE);

        int page = reqPage != null ? Integer.parseInt(reqPage) : 1;

        logger.debug("Search all tests request received");
        List<Quiz> tests = this.searchAll(page, req, recOnPage, course.getId());

        req.setAttribute(RequestConstant.TESTS, tests);
        return new ViewResolver(PageConstant.COURSE_TESTS_PAGE);
    }

    private List<Quiz> searchAll(int page, HttpServletRequest req, int recOnPage, long courseId)
            throws ServiceException {

        Integer count = courseUserService.countAllTests(courseId);
        int start = this.definePagination(req, count, page, recOnPage);
        return courseUserService.readAllCourseTests(start, recOnPage, courseId);
    }
}
