package by.alex.testing.controller.command;

import by.alex.testing.controller.NotEnoughParametersException;
import by.alex.testing.controller.RequestConstant;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ParamsFromRequestHandler {

    private ParamsFromRequestHandler(){
    }

    public static Long getCourseIdParameter(HttpServletRequest req)
            throws NotEnoughParametersException {

        String courseIdParam = req.getParameter(RequestConstant.COURSE_ID);
        Long courseId;
        if (StringUtils.isNullOrEmpty(courseIdParam)) {
            courseId = (Long) req.getSession().getAttribute(RequestConstant.COURSE_ID);
            if (courseId == null) {
                throw new NotEnoughParametersException();
            }
        } else {
            courseId = Long.valueOf(courseIdParam);
            req.getSession().setAttribute(RequestConstant.COURSE_ID, courseId);
        }
        return courseId;
    }

    public static Long getLessonIdParameter(HttpServletRequest req)
            throws NotEnoughParametersException {

        String courseIdParam = req.getParameter(RequestConstant.LESSON_ID);
        Long lessonId;
        if (StringUtils.isNullOrEmpty(courseIdParam)) {
            lessonId = (Long) req.getSession().getAttribute(RequestConstant.LESSON_ID);
            if (lessonId == null) {
                throw new NotEnoughParametersException();
            }
        } else {
            lessonId = Long.valueOf(courseIdParam);
            req.getSession().setAttribute(RequestConstant.LESSON_ID, lessonId);
        }
        return lessonId;
    }
}
