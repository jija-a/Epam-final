package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.CommonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTeacherCoursesCommand implements Command {

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    private final TeacherService teacherService;
    private final CommonService commonService;

    public ShowTeacherCoursesCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
        this.commonService = ServiceFactory.getInstance().getCommonService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) throws ServiceException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long teacherId = user.getId();

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);

        Integer count = teacherService.countAllCoursesByTeacherId(teacherId);
        int start = this.definePagination(req, count, recordsPerPage, DEFAULT_PAGINATION_LIMIT);
        List<Course> courses =
                teacherService.findAllCoursesByTeacherId(start, recordsPerPage, teacherId);

        List<CourseCategory> categories = commonService.readAllCourseCategories();
        req.setAttribute(RequestConstant.COURSE_CATEGORIES, categories);
        req.setAttribute(RequestConstant.COURSES, courses);
        req.getSession().removeAttribute(RequestConstant.COURSE_ID);
        return new ViewResolver(PageConstant.COURSES_LIST_PAGE);
    }
}
