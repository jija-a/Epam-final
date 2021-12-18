package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteCourseCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * Class constructor. Initializes service.
     */
    public DeleteCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, AccessException {

        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        long courseId =
                Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));
        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);

        if (teacherService.deleteCourse(courseId, teacher)) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.DELETED);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.DELETE_ERROR);
            req.setAttribute(RequestConstant.ERROR, msg);
        }

        return resolver;
    }
}
