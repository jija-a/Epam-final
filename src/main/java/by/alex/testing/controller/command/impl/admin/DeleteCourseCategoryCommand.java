package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCourseCategoryCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteCourseCategoryCommand.class);

    private final CourseCategoryService categoryService;

    public DeleteCourseCategoryCommand() {
        categoryService = ServiceFactory.getInstance().getCourseCategoryService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete course category command received");

        String categoryId = req.getParameter(RequestConstant.COURSE_CATEGORY_ID);

        if (!StringUtils.isNullOrEmpty(categoryId)) {
            categoryService.deleteCourseCategory(Long.parseLong(categoryId));
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));
        }

        String page = createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
