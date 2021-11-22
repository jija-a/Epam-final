package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowCoursesCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowCoursesCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final CourseService courseService;

    public ShowCoursesCommand() {
        courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("View courses command received");
        List<Course> courses = new ArrayList<>();

        String stringRec = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recOnPage = stringRec == null ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(stringRec);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recOnPage);

        String search = req.getParameter(RequestConstant.SEARCH);
        String reqPage = req.getParameter(RequestConstant.PAGE);

        int page = reqPage != null ? Integer.parseInt(reqPage) : 1;

        if (!StringUtils.isNullOrEmpty(search)) {
            logger.debug("Search courses request by '{}' received", search);
            courses = this.searchByName(search, page, req, recOnPage);
        }
        if (courses.isEmpty()) {
            logger.debug("Search all courses request received");
            courses = this.searchAll(page, req, recOnPage);
        }

        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }

    private List<Course> searchByName(String search, int page, HttpServletRequest req, int recOnPage)
            throws ServiceException {

        search = search.trim();
        req.setAttribute(RequestConstant.SEARCH, search);
        Integer count = courseService.countAllCourses(search);
        int start = this.definePagination(req, count, page, recOnPage);
        return courseService.readCourseByTitle(start, recOnPage, search);
    }

    private List<Course> searchAll(int page, HttpServletRequest req, int recOnPage)
            throws ServiceException {

        Integer count = courseService.countAllCourses();
        int start = this.definePagination(req, count, page, recOnPage);
        return courseService.readAllCourses(start, recOnPage);
    }
}
