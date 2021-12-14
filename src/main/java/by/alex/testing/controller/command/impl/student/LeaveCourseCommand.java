package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.AccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeaveCourseCommand implements Command {

    private final StudentService studentService;

    public LeaveCourseCommand() {
        this.studentService = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException {

        String page = createRedirectURL(req, CommandName.SHOW_STUDENT_COURSES);
        ViewResolver resolver = new ViewResolver(page);

        User student = (User) req.getSession().getAttribute(RequestConstant.USER);
        long courseId = Long.parseLong(req.getParameter(RequestConstant.COURSE_ID));

        CourseUser courseUser = CourseUser.builder()
                .user(student)
                .course(Course.builder().id(courseId).build())
                .status(UserCourseStatus.ON_COURSE)
                .build();

        if (studentService.leaveCourse(courseUser)) {
            req.getSession().setAttribute(RequestConstant.SUCCESS,
                    MessageManager.INSTANCE.getMessage(MessageConstant.UNSIGNED));
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.CANT_UNSIGNED));
        }
        return resolver;
    }
}
