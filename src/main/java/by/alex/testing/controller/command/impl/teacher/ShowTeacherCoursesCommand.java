package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTeacherCoursesCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowTeacherCoursesCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final TeacherService teacherService;
    private final CourseCategoryService courseCategoryService;

    public ShowTeacherCoursesCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
        this.courseCategoryService = ServiceFactory.getInstance().getCourseCategoryService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("View teacher courses command received");
        List<Course> courses;

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long userId = user.getId();

        String stringRec = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recOnPage = stringRec == null ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(stringRec);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recOnPage);

        String reqPage = req.getParameter(RequestConstant.PAGE);
        int page = reqPage != null ? Integer.parseInt(reqPage) : 1;

        courses = this.searchAll(page, req, recOnPage, userId);

        List<CourseCategory> categories = courseCategoryService.readAllCourseCategories();

        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }

    private List<Course> searchAll(int page, HttpServletRequest req, int recOnPage, long userId)
            throws ServiceException {

        Integer count = teacherService.countAllCourses(userId);
        int start = this.definePagination(req, count, page, recOnPage);
        return teacherService.readAllCourses(start, recOnPage, userId);
    }
}
