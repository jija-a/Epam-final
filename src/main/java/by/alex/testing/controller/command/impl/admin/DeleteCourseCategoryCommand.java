package by.alex.testing.controller.command.impl.admin;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.DaoException;
import by.alex.testing.service.CourseService;
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

    private final CourseService courseService;

    public DeleteCourseCategoryCommand() {
        courseService = ServiceFactory.getInstance().getCourseService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete course category command received");

        String categoryId = req.getParameter(RequestConstant.COURSE_CATEGORY_ID);

        if (!StringUtils.isNullOrEmpty(categoryId)) {
            courseService.deleteCourseCategory(Long.parseLong(categoryId));
        }

        String page = createRedirectURL(req, CommandName.SHOW_COURSE_CATEGORIES);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
