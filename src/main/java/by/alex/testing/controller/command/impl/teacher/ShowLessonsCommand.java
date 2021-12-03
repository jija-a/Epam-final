package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamsFromRequestHandler;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowLessonsCommand implements Command {

    private final TeacherService teacherService;

    private static final int DEFAULT_PAGINATION_LIMIT = 5;

    public ShowLessonsCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException {

        ViewResolver resolver = new ViewResolver(PageConstant.LESSONS_VIEW);

        String recordsParam = req.getParameter(RequestConstant.RECORDS_PER_PAGE);
        int recordsPerPage = StringUtils.isNullOrEmpty(recordsParam) ? DEFAULT_PAGINATION_LIMIT :
                Integer.parseInt(recordsParam);
        long courseId = ParamsFromRequestHandler.getCourseIdParameter(req);

        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);
        int count = teacherService.countAllLessons(courseId);
        int start = this.definePagination(req, count, recordsPerPage);

        try {
            List<Lesson> lessons = teacherService.findAllLessons(courseId, start, recordsPerPage, teacher);
            req.setAttribute(RequestConstant.LESSONS, lessons);
        } catch (CourseAccessDeniedException e) {
            logger.info(e.getMessage());
            String page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
            resolver.setView(page);
        }
        return resolver;
    }
}
