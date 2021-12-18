package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.ParamFromReqHandler;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.User;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.AttendanceService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TeacherService;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class UpdateAttendanceCommand implements Command {

    /**
     * @see TeacherService
     */
    private final TeacherService teacherService;

    /**
     * @see AttendanceService
     */
    private final AttendanceService attendanceService;

    /**
     * Class constructor. Initializes service.
     */
    public UpdateAttendanceCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        this.teacherService = factory.getTeacherService();
        this.attendanceService = factory.getAttendanceService();
    }

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException {

        String page = createRedirectURL(req, CommandName.SHOW_ATTENDANCES);
        ViewResolver resolver = new ViewResolver(page);

        User teacher = (User) req.getSession()
                .getAttribute(RequestConstant.USER);
        long attendanceId =
                Long.parseLong(req.getParameter(RequestConstant.ATTENDANCE_ID));
        long courseId = ParamFromReqHandler
                .getLongParameter(req, RequestConstant.COURSE_ID);

        Attendance attendance = attendanceService.findAttendance(attendanceId);
        String markParam = req.getParameter(RequestConstant.MARK);
        Integer mark = StringUtils.isNullOrEmpty(markParam)
                ? null : Integer.valueOf(markParam);
        int statusId =
                Integer.parseInt(req.getParameter(RequestConstant.STATUS_ID));
        attendance.setMark(mark);
        attendance.setStatus(AttendanceStatus.resolveStatusById(statusId));

        List<String> errors = teacherService
                .updateAttendance(attendance, courseId, teacher);
        if (errors.isEmpty()) {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.UPDATED_SUCCESS);
            req.getSession().setAttribute(RequestConstant.SUCCESS, msg);
            resolver.setResolveAction(ViewResolver.ResolveAction.REDIRECT);
        } else {
            req.setAttribute(RequestConstant.ERROR, errors);
        }
        return resolver;
    }
}
