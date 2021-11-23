package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowCourseCategoriesCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowCourseCategoriesCommand.class);

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final CourseCategoryService courseService;

    public ShowCourseCategoriesCommand() {
        courseService = ServiceFactory.getInstance().getCourseCategoryService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("View courses command received");
        List<CourseCategory> categories = new ArrayList<>();

        String stringRec = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recOnPage = stringRec == null ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(stringRec);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recOnPage);

        String search = req.getParameter(RequestConstant.SEARCH);
        String reqPage = req.getParameter(RequestConstant.PAGE);

        int page = reqPage != null ? Integer.parseInt(reqPage) : 1;

        if (!StringUtils.isNullOrEmpty(search)) {
            logger.debug("Search course categories request by '{}' received", search);
            categories = this.searchByName(search, page, req, recOnPage);
        }
        if (categories.isEmpty()) {
            logger.debug("Search all course categories request received");
            categories = this.searchAll(page, req, recOnPage);
        }

        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        logger.debug("Course categories - {}", categories);
        return new ViewResolver(PageConstant.COURSE_CATEGORIES_LIST_PAGE);
    }

    private List<CourseCategory> searchByName(String search, int page, HttpServletRequest req, int recOnPage)
            throws ServiceException {

        search = search.trim();
        req.setAttribute(RequestConstant.SEARCH, search);
        Integer count = courseService.countAllCourseCategories(search);
        int start = this.definePagination(req, count, page, recOnPage);
        logger.debug("page - {}, start - {}, recOnPage - {}, count - {}", page, start, recOnPage, count);
        return courseService.readCourseCategoriesByTitle(start, recOnPage, search);
    }

    private List<CourseCategory> searchAll(int page, HttpServletRequest req, int recOnPage)
            throws ServiceException {

        Integer count = courseService.countAllCourseCategories();
        int start = this.definePagination(req, count, page, recOnPage);
        logger.debug("page - {}, start - {}, recOnPage - {}, count - {}", page, start, recOnPage, count);
        return courseService.readAllCourseCategories(start, recOnPage);
    }
}
