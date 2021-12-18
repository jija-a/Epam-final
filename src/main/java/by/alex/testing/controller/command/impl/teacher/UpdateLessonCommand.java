package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.ParameterValidator;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.LessonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class UpdateLessonCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * @see LessonService
     */
    private final LessonService lessonService;

    /**
     * Class constructor. Initializes service.
     */
    public UpdateLessonCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.teacherService = factory.getTeacherService();
        this.lessonService = factory.getLessonService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        String page = createRedirectURL(req, CommandName.SHOW_LESSONS);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        Lesson lesson = this.findAndUpdateFields(req);

        List<String> errors = teacherService.updateLesson(lesson, teacher);
        if (errors.isEmpty()) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.UPDATED_SUCCESS);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERRORS, errors);
        }
        return resolver;
    }

    private Lesson findAndUpdateFields(final HttpServletRequest req)
            throws ParametersException, ServiceException {

        String lessonIdParam = req.getParameter(RequestConstant.LESSON_ID);
        String title = req.getParameter(RequestConstant.LESSON_TITLE);

        if (ParameterValidator.isNullOrEmpty(lessonIdParam, title)) {
            throw new ParametersException();
        }
        title = title.trim();
        long lessonId = Long.parseLong(lessonIdParam);

        Lesson lesson = lessonService.findLessonById(lessonId);
        lesson.setTitle(title);

        return lesson;
    }
}
