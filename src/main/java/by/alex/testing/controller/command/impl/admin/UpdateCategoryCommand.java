package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.AdminService;
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

    private final AdminService adminService;

    public UpdateCategoryCommand() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException {

        logger.info("Update course category command received");
        String page = createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        ViewResolver resolver = new ViewResolver(page);

        String categoryId = req.getParameter(RequestConstant.COURSE_CATEGORY_ID);
        String name = req.getParameter(RequestConstant.COURSE_CATEGORY_NAME);

        if (adminService.readCategoryByTitle(name) != null) {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.ALREADY_EXISTS));
            return resolver;
        }

        if (!StringUtils.isNullOrEmpty(categoryId)) {
            CourseCategory category = adminService.readCategoryById(Long.parseLong(categoryId));
            category.setName(name);
            List<String> errors = adminService.updateCategory(category);
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
