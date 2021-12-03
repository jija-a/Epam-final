package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.validator.BaseParameterValidator;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineStudentRequestCommand implements Command {

    private final TeacherService teacherService;

    public DeclineStudentRequestCommand() {
        this.teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        String view = createRedirectURL(req, CommandName.SHOW_COURSE_REQUESTS);
        ViewResolver resolver = new ViewResolver(view);

        String courseIdParam = req.getParameter(RequestConstant.COURSE_ID);
        String studentIdParam = req.getParameter(RequestConstant.USER_ID);

        if (BaseParameterValidator.isNullOrEmpty(courseIdParam, studentIdParam)) {
            throw new NotEnoughParametersException("Not enough parameters for executing command");
        }
        long courseId = Long.parseLong(courseIdParam);
        long studentId = Long.parseLong(studentIdParam);
        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);

        try {
            if (this.refuseRequest(courseId, studentId, teacher)) {
                req.getSession().setAttribute(RequestConstant.SUCCESS,
                        MessageManager.INSTANCE.getMessage(MessageConstant.DECLINE_SUCCESS));
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            }
        } catch (CourseAccessDeniedException e) {
            logger.info(e.getMessage());
        }
        return resolver;
    }

    private boolean refuseRequest(long courseId, long studentId, User teacher) throws ServiceException, CourseAccessDeniedException {
        CourseUser courseUser = teacherService.findCourseUser(courseId, studentId);
        if (courseUser == null) {
            return false;
        }
        return teacherService.deleteCourseUser(courseUser, teacher);
    }
}
