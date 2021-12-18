package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowCoursesCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see CourseService
     */
    private final CourseService courseService;

    /**
     * @see CourseCategoryService
     */
    private final CourseCategoryService courseCategoryService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowCoursesCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.courseService = factory.getCourseService();
        this.courseCategoryService = factory.getCourseCategoryService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        List<Course> courses;
        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (StringUtils.isNullOrEmpty(search)) {
            courses = this.findAll(req, recordsPerPage);
        } else {
            courses = this.findByRequest(req, recordsPerPage, search);
        }

        List<CourseCategory> categories =
                courseCategoryService.findAllCategories();
        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }

    private List<Course> findAll(final HttpServletRequest req,
                                 final int recordsPerPage)
            throws ServiceException {

        int count = courseService.countAllCourses();
        int start = this.definePagination(req, count, recordsPerPage);
        return courseService.findAllCourses(start, recordsPerPage);
    }

    private List<Course> findByRequest(final HttpServletRequest req,
                                       final int recordsPerPage,
                                       final String search)
            throws ServiceException {

       req.setAttribute(RequestConstant.SEARCH, search.trim());
        int count = courseService.countAllCourses(search.trim());
        int start = this.definePagination(req, count, recordsPerPage);
        return courseService
                .findCoursesByTitle(start, recordsPerPage, search.trim());
    }
}
