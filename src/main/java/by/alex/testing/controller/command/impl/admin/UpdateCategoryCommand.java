package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
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

public final class UpdateCategoryCommand implements Command {

    /**
     * @see CourseCategoryService
     */
    private final CourseCategoryService courseCategoryService;

    /**
     * Class constructor. Initializes service.
     */
    public UpdateCategoryCommand() {
        courseCategoryService =
                ServiceFactory.getInstance().getCourseCategoryService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        String page =
                createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        ViewResolver resolver = new ViewResolver(page);

        String categoryId =
                req.getParameter(RequestConstant.COURSE_CATEGORY_ID);
        String name = req.getParameter(RequestConstant.COURSE_CATEGORY_NAME);

        if (!StringUtils.isNullOrEmpty(categoryId)) {
            long id = Long.parseLong(categoryId);
            CourseCategory category = courseCategoryService.findCategory(id);
            category.setName(name.trim());
            List<String> errors =
                    courseCategoryService.updateCategory(category);
            if (errors.isEmpty()) {
                String msg = MessageManager.INSTANCE
                        .getMessage(MessageConstant.UPDATED_SUCCESS);
                req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERRORS, errors);
            }
        }

        return resolver;
    }
}
