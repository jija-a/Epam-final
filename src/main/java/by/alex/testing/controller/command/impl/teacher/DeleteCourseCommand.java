package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCourseCommand implements Command {

    private final TeacherService teacherService;

    public DeleteCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, AccessDeniedException {

        String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        long courseId = Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));
        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);

        if (teacherService.deleteCourse(courseId, teacher)) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.DELETE_ERROR));
        }

        return resolver;
    }
}
