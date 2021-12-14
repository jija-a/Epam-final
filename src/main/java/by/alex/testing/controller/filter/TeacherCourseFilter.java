package by.alex.testing.controller.filter;

import by.alex.testing.controller.RequestConstant;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeacherCourseFilter extends BaseFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        boolean isAccessible = false;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        UserRole role = user.getRole();
        String courseId = req.getParameter(RequestConstant.COURSE_ID);
        if (role.equals(UserRole.TEACHER) && !courseId.isEmpty()) {
            long reqId = Long.parseLong(courseId);
            int[] availableCourseId = (int[]) req.getSession().getAttribute(RequestConstant.COURSES_ID);
            for (int i = 0; i < availableCourseId.length - 1; i++) {
                if (availableCourseId[i] == reqId) {
                    isAccessible = true;
                    break;
                }
            }
            if (isAccessible) {
                chain.doFilter(request, response);
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
