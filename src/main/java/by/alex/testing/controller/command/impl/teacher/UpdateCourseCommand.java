package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.impl.CourseServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateCourseCommand implements Command {

    private final CourseService service;

    public UpdateCourseCommand() {
        this.service = new CourseServiceImpl();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        String courseId = req.getParameter(RequestConstant.COURSE_ID);
        String courseTitle = req.getParameter(RequestConstant.COURSE_TITLE);
        long courseCategoryId = Long.parseLong(req.getParameter(RequestConstant.COURSE_CATEGORY_ID));

        Course course = service.readCourseById(Long.parseLong(courseId));
        course.setCategory(CourseCategory.builder().id(courseCategoryId).build());
        course.setName(courseTitle);
        service.updateCourseInfo(course);

        return new ViewResolver(PageConstant.HOME_PAGE);
    }
}
