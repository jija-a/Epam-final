package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.BaseParameterValidator;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateLessonCommand implements Command {

    private final TeacherService teacherService;

    public UpdateLessonCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException {

        String page = createRedirectURL(req, CommandName.SHOW_LESSONS);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);
        Lesson lesson = this.findAndUpdateFields(req);

        List<String> errors = teacherService.updateLesson(lesson, teacher);
        if (errors.isEmpty()) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.UPDATED_SUCCESS));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERRORS, errors);
        }
        return resolver;
    }

    private Lesson findAndUpdateFields(HttpServletRequest req)
            throws NotEnoughParametersException, ServiceException {

        String lessonIdParam = req.getParameter(RequestConstant.LESSON_ID);
        String title = req.getParameter(RequestConstant.LESSON_TITLE);
        String startDate = req.getParameter(RequestConstant.START_DATE);
        String endDate = req.getParameter(RequestConstant.END_DATE);

        if (BaseParameterValidator.isNullOrEmpty(lessonIdParam, title, startDate, endDate)) {
            throw new NotEnoughParametersException("Not enough parameters for executing command");
        }
        long lessonId = Long.parseLong(lessonIdParam);

        Lesson lesson = teacherService.findLessonById(lessonId);
        lesson.setTitle(title);
        lesson.setStartDate(LocalDateTime.parse(startDate));
        lesson.setEndDate(LocalDateTime.parse(endDate));

        return lesson;
    }
}
