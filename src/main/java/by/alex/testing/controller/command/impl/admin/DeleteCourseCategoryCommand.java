package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteCourseCategoryCommand implements Command {

    /**
     * @see CourseCategoryService
     */
    private final CourseCategoryService courseCategoryService;

    /**
     * Class constructor. Initializes service.
     */
    public DeleteCourseCategoryCommand() {
        courseCategoryService =
                ServiceFactory.getInstance().getCourseCategoryService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException {

        String catId = req.getParameter(RequestConstant.COURSE_CATEGORY_ID);
        if (!StringUtils.isNullOrEmpty(catId)) {
            courseCategoryService.deleteCategory(Long.parseLong(catId));
            String msg =
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETED);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
        }

        String page =
                createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
