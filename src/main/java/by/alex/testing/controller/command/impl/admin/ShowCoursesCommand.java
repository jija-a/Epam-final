package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CommonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCoursesCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowCoursesCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final CommonService commonService;

    public ShowCoursesCommand() {
        this.commonService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        List<Course> courses;

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (!StringUtils.isNullOrEmpty(search)) {
            req.setAttribute(RequestConstant.SEARCH, search);
            courses = this.findBySearchRequest(req, recordsPerPage, search);
            if (courses.isEmpty()) {
                req.setAttribute(RequestConstant.ERROR,
                        MessageManager.INSTANCE.getMessage(MessageConstant.NOT_FOUND));
            }
        } else {
            courses = this.findAll(req, recordsPerPage);
        }

        List<CourseCategory> categories = commonService.readAllCourseCategories();
        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }

    private List<Course> findAll(HttpServletRequest req, int recordsPerPage) throws ServiceException {
        logger.debug("Search all courses");
        int count = commonService.countAllCourses();
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
        return commonService.readAllCourses(start, recordsPerPage);
    }

    private List<Course> findBySearchRequest(HttpServletRequest req, int recordsPerPage, String search)
            throws ServiceException {
        logger.debug("Search courses by '{}'", search);
        int count = commonService.countAllCourses(search.trim());
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
        return commonService.readCourseByTitle(start, recordsPerPage, search.trim());
    }
}
