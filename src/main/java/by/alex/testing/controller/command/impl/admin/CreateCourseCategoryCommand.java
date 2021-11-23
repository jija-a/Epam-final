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

public class CreateCourseCategoryCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(CreateCourseCategoryCommand.class);

    private final CourseCategoryService categoryService;

    public CreateCourseCategoryCommand() {
        categoryService = ServiceFactory.getInstance().getCourseCategoryService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Create course category command received");
        String page = createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        ViewResolver resolver = new ViewResolver(page);

        String categoryName = req.getParameter(RequestConstant.COURSE_CATEGORY_NAME);

        if (!StringUtils.isNullOrEmpty(categoryName)) {
            CourseCategory category = new CourseCategory(categoryName);
            List<String> errors = categoryService.create(category);
            if (errors.isEmpty()) {
                req.getSession().setAttribute(RequestConstant.SUCCESS,
                        MessageManager.INSTANCE.getMessage(MessageConstant.CREATE_SUCCESS));
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERRORS, errors);
            }
        }

        return resolver;
    }
}
