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

public class DeleteCourseCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteCourseCommand.class);

    private final CourseService courseService;

    public DeleteCourseCommand() {
        courseService = ServiceFactory.getInstance().getCourseService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException {

        logger.info("Delete course command received");

        String courseId = req.getParameter(RequestConstant.COURSE_ID);

        if (!StringUtils.isNullOrEmpty(courseId)) {
            courseService.deleteCourse(Long.parseLong(courseId));
        }

        String page = createRedirectURL(req, CommandName.SHOW_COURSES);
        return new ViewResolver(page, ViewResolver.ResolveAction.REDIRECT);
    }
}
