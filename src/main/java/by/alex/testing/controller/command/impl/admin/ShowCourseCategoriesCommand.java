package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.AdminService;
import by.alex.testing.service.PaginationService;
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

    private final AdminService adminService;
    private final PaginationService paginationService;

    public ShowCourseCategoriesCommand() {
        adminService = ServiceFactory.getInstance().getAdminService();
        paginationService = ServiceFactory.getInstance().getPaginationService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        logger.info("View courses command received");

        int pageLimit = req.getParameter(RequestConstant.RECORDS_PER_PAGE) == null
                ? DEFAULT_PAGINATION_LIMIT : Integer.parseInt(req.getParameter(RequestConstant.RECORDS_PER_PAGE));
        int page = req.getParameter(RequestConstant.PAGE_NUMBER) != null ?
                Integer.parseInt(req.getParameter(RequestConstant.PAGE_NUMBER)) : 1;
        String search = req.getParameter(RequestConstant.SEARCH);

        List<CourseCategory> categories = new ArrayList<>();
        int pages = 1;
        int entitiesQty;
        int start;
        if (!StringUtils.isNullOrEmpty(search)) {
            req.setAttribute(RequestConstant.SEARCH, search);
            entitiesQty = adminService.countAllCourseCategories(search);
            start = paginationService.defineStartEntityNumber(page, pageLimit);
            pages = paginationService.defineNumberOfPages(entitiesQty, pageLimit);
            categories = adminService.readAllCourseCategories(start, pageLimit, search);
            if (categories.isEmpty()){
                req.setAttribute(RequestConstant.ERROR,
                        MessageManager.INSTANCE.getMessage(MessageConstant.NOT_FOUND));
            }
        } else {
            entitiesQty = adminService.countAllCourseCategories();
            start = paginationService.defineStartEntityNumber(page, pageLimit);
            pages = paginationService.defineNumberOfPages(entitiesQty, pageLimit);
            categories = adminService.readAllCourseCategories(start, pageLimit);
         }

        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, pageLimit);
        req.setAttribute(RequestConstant.NUMBER_OF_PAGES, pages);
        req.setAttribute(RequestConstant.PAGE_NUMBER, page);

        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        return new ViewResolver(PageConstant.COURSE_CATEGORIES_LIST_PAGE);
    }
}
