package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class ShowRequestedCoursesCommand implements Command {

    /**
     * @see StudentService
     */
    private final StudentService service;

    /**
     * Class constructor. Initializes service.
     */
    public ShowRequestedCoursesCommand() {
        this.service = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long id = user.getId();

        List<Course> courses =
                service.readStudentCourses(id, UserCourseStatus.REQUESTED);
        req.setAttribute(RequestConstant.COURSES, courses);
        return new ViewResolver(PageConstant.COURSE_REQUESTS);
    }
}
