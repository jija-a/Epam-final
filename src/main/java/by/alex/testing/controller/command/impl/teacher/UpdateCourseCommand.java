package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UpdateCourseCommand implements Command {

    private final CourseService courseService;

    public UpdateCourseCommand() {
        this.courseService = ServiceFactory.getInstance().getCourseService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);
        String courseTitle = req.getParameter(RequestConstant.COURSE_TITLE);
        long courseCategoryId = Long.parseLong(req.getParameter(RequestConstant.COURSE_CATEGORY_ID));

        Course course = courseService.readCourseById(Long.parseLong(courseId));
        course.setCategory(CourseCategory.builder().id(courseCategoryId).build());
        course.setName(courseTitle);
        courseService.updateCourseInfo(course);

        HttpSession session = req.getSession();
        List<Course> courses = courseService.readCourseByTitle("");
        session.setAttribute(RequestConstant.MESSAGE, "Update course '" + course.getName() + "' successful");
        session.setAttribute(RequestConstant.COURSES, courses);

        return new ViewResolver(PageConstant.COURSES, ViewResolver.ResolveAction.REDIRECT);
    }
}
