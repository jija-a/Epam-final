package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdateCategoryCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(UpdateCategoryCommand.class);

    private final CourseCategoryService categoryService;

    public UpdateCategoryCommand() {
        categoryService = ServiceFactory.getInstance().getCourseCategoryService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Update course category command received");
        String page = createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        ViewResolver resolver = new ViewResolver(page);

        String categoryId = req.getParameter(RequestConstant.COURSE_CATEGORY_ID);

        if (!StringUtils.isNullOrEmpty(categoryId)) {
            CourseCategory category = categoryService.readCategoryById(Long.parseLong(categoryId));
            category.setName(req.getParameter(RequestConstant.COURSE_CATEGORY_NAME));
            List<String> errors = categoryService.updateCategory(category);
            if (errors.isEmpty()) {
                req.getSession().setAttribute(RequestConstant.SUCCESS,
                        MessageManager.INSTANCE.getMessage(MessageConstant.UPDATED_SUCCESS));
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERRORS, errors);
            }
        }

        return resolver;
    }
}