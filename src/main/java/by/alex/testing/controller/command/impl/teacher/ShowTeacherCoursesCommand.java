package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowTeacherCoursesCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see CourseCategoryService
     */
    private final CourseCategoryService courseCategoryService;

    /**
     * @see CourseService
     */
    private final CourseService courseService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowTeacherCoursesCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.courseCategoryService = factory.getCourseCategoryService();
        this.courseService = factory.getCourseService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        User user = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        long teacherId = user.getId();

        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        Integer count = courseService.countAllCoursesByTeacherId(teacherId);
        int start = this.definePagination(req, count, recordsPerPage);
        List<Course> courses = courseService
                .findAllCoursesByTeacherId(start, recordsPerPage, teacherId);

        List<CourseCategory> categories =
                courseCategoryService.findAllCategories();
        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        req.setAttribute(RequestConstant.COURSES, courses);
        req.getSession().removeAttribute(RequestConstant.COURSE_ID);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }
}
