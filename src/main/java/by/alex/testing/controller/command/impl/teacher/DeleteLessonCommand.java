package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteLessonCommand implements Command {

    private final TeacherService teacherService;

    public DeleteLessonCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }


    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException {

        String page = createRedirectURL(req, CommandName.SHOW_LESSONS);
        ViewResolver resolver = new ViewResolver(page);

        long lessonId = Long.parseLong(req.getParameter(RequestConstant.LESSON_ID));
        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);

        try {
            if (teacherService.deleteLesson(lessonId, teacher)) {
                req.getSession().setAttribute(RequestConstant.SUCCESS,
                        MessageManager.INSTANCE.getMessage(MessageConstant.DELETED));
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERROR,
                        MessageManager.INSTANCE.getMessage(MessageConstant.DELETE_ERROR));
            }
        } catch (CourseAccessDeniedException e) {
            logger.info(e.getMessage());
        }

        return resolver;
    }
}
