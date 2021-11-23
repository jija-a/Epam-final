package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.RequestConstant;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

public class TeacherActionResolver {

    private static CourseService courseService = ServiceFactory.getInstance().getCourseService();

    private TeacherActionResolver(){
    }

    public static Course resolvePermission(HttpServletRequest req) throws ServiceException {
        String courseId = req.getParameter(RequestConstant.COURSE_ID);
        if (courseId == null) {
            courseId = String.valueOf(req.getSession().getAttribute(RequestConstant.COURSE_ID));
        }
        Course course = courseService.readCourseById(Long.parseLong(courseId));
        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        if (course != null && course.getOwner().getId().equals(user.getId())) {
            return course;
        }
        return null;
    }
}
