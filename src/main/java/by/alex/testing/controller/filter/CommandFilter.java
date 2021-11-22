package by.alex.testing.controller.filter;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*@WebFilter(filterName = "CommandFilter",
        urlPatterns = "/controller?")*/
public class CommandFilter extends BaseFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(CommandFilter.class);

    private static final Set<String> COMMON_COMMANDS = initCommonCommands();

    private static final Set<String> STUDENT_COMMANDS = initStudentCommands();

    private static final Set<String> TEACHER_COMMANDS = initTeacherCommands();

    private static final Set<String> ADMIN_COMMANDS = initAdminCommands();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(RequestConstant.USER);
        UserRole role = user != null ? user.getRole() : UserRole.GUEST;
        Set<String> accessibleCommands;
        switch (role) {
            case GUEST:
                accessibleCommands = COMMON_COMMANDS;
                break;
            case STUDENT:
                accessibleCommands = STUDENT_COMMANDS;
                break;
            case TEACHER:
                accessibleCommands = TEACHER_COMMANDS;
                break;
            case ADMIN:
                accessibleCommands = ADMIN_COMMANDS;
                break;
            default:
                accessibleCommands = COMMON_COMMANDS;
        }

        String commandName = req.getParameter(RequestConstant.COMMAND);

        if (!StringUtils.isNullOrEmpty(commandName)) {
            if (accessibleCommands.contains(commandName)) {
                logger.info("User have permission");
                chain.doFilter(request, response);
            } else {
                logger.info("User do not have permission");
                String page = req.getContextPath() + req.getServletPath() + "?" + RequestConstant.COMMAND + "=" + CommandName.TO_LOGIN_PAGE;
                res.sendRedirect(page);
            }
        }

    }

    @Override
    public void destroy() {
    }

    private static Set<String> initCommonCommands() {
        Set<String> commands = new HashSet<>();
        commands.add(CommandName.TO_LOGIN_PAGE);
        commands.add(CommandName.TO_REGISTRATION_PAGE);
        commands.add(CommandName.REGISTER);
        commands.add(CommandName.LOGIN);
        return commands;
    }

    private static Set<String> initStudentCommands() {
        Set<String> commands = new HashSet<>();
        commands.add(CommandName.TO_HOME_PAGE);
        commands.add(CommandName.LOGOUT);
        commands.add(CommandName.CHANGE_LOCALE);
        return commands;
    }

    private static Set<String> initTeacherCommands() {
        Set<String> commands = new HashSet<>();
        commands.add(CommandName.TO_HOME_PAGE);
        commands.add(CommandName.LOGOUT);
        commands.add(CommandName.CHANGE_LOCALE);
        return commands;
    }

    private static Set<String> initAdminCommands() {
        Set<String> commands = new HashSet<>();
        commands.add(CommandName.TO_HOME_PAGE);
        commands.add(CommandName.LOGOUT);
        commands.add(CommandName.CHANGE_LOCALE);
        return commands;
    }
}
