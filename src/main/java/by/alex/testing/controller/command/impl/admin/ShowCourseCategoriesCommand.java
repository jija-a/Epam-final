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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowCourseCategoriesCommand implements Command {

    /**
     * Default max quantity of entities on page.
     */
    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    /**
     * @see CourseCategoryService
     */
    private final CourseCategoryService categoryService;

    /**
     * Class constructor. Initializes service.
     */
    public ShowCourseCategoriesCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        categoryService = factory.getCourseCategoryService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        List<CourseCategory> categories;
        String recordsParam =
                req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam)
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(recordsParam);

        String search = req.getParameter(RequestConstant.SEARCH);
        if (StringUtils.isNullOrEmpty(search)) {
            categories = this.findAll(req, recordsPerPage);
        } else {
            categories = this.findByRequest(req, recordsPerPage, search);
        }
        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        return new ViewResolver(PageConstant.COURSE_CATEGORIES_LIST_PAGE);
    }

    private List<CourseCategory> findAll(final HttpServletRequest req,
                                         final int recordsPerPage)
            throws ServiceException {

        int entitiesQty = categoryService.countCategories();
        int start = this.definePagination(req, entitiesQty, recordsPerPage);
        return categoryService.findAllCategories(start, recordsPerPage);
    }

    private List<CourseCategory> findByRequest(final HttpServletRequest req,
                                               final int recordsPerPage,
                                               final String search)
            throws ServiceException {

        req.setAttribute(RequestConstant.SEARCH, search);
        int entitiesQty = categoryService.countCategories(search);
        int start = this.definePagination(req, entitiesQty, recordsPerPage);
        return categoryService.findAllCategories(start, recordsPerPage, search);
    }
}
