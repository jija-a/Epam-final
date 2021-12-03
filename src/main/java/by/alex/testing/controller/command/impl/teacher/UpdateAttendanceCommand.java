package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.*;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.UnknownEntityException;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdateAttendanceCommand implements Command {

    private final TeacherService teacherService;

    public UpdateAttendanceCommand() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, UnknownEntityException {

        String page = createRedirectURL(req, CommandName.SHOW_ATTENDANCES);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession().getAttribute(RequestConstant.USER);
        long attendanceId = Long.parseLong(req.getParameter(RequestConstant.ATTENDANCE_ID));
        String markParam = req.getParameter(RequestConstant.MARK);
        Integer mark = StringUtils.isNullOrEmpty(markParam) ? null : Integer.valueOf(markParam);
        int statusId = Integer.parseInt(req.getParameter(RequestConstant.STATUS_ID));

        Attendance attendance = teacherService.findAttendance(attendanceId);
        attendance.setMark(mark);
        attendance.setStatus(AttendanceStatus.resolveStatusById(statusId));

        try {
            List<String> errors = teacherService.updateAttendance(attendance, teacher);
            if (errors.isEmpty()) {
                req.getSession().setAttribute(RequestConstant.SUCCESS,
                        MessageManager.INSTANCE.getMessage(MessageConstant.UPDATED_SUCCESS));
                resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
            } else {
                req.setAttribute(RequestConstant.ERROR, errors);
            }
        } catch (CourseAccessDeniedException e) {
            logger.info(e.getMessage());
            page = createRedirectURL(req, CommandName.SHOW_TEACHER_COURSES);
            resolver.setView(page);
        }
        return resolver;
    }
}
