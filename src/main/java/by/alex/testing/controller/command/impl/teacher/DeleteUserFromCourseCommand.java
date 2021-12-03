package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserFromCourseCommand implements Command {

    private final TeacherService teacherService;

    public DeleteUserFromCourseCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        String page = createRedirectURL(req, CommandName.SHOW_COURSE_USERS);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);
        Long courseId = (Long) req.getSession().getAttribute(RequestConstant.COURSE_ID);
        String userIdParam = req.getParameter(RequestConstant.USER_ID);

        if (StringUtils.isNullOrEmpty(userIdParam) || courseId == null) {
            throw new NotEnoughParametersException("Params are missing");
        }
        long userId = Long.parseLong(userIdParam);

        try {
            if (this.removeUser(courseId, userId, teacher)) {
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

    private boolean removeUser(Long courseId, long userId, User teacher)
            throws ServiceException, CourseAccessDeniedException {
        CourseUser courseUser = CourseUser.builder()
                .user(User.builder().id(userId).build())
                .course(Course.builder().id(courseId).build())
                .status(UserCourseStatus.ON_COURSE)
                .build();

        return teacherService.deleteUserFromCourse(courseUser, teacher);
    }
}
