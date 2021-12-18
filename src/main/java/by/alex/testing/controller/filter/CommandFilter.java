package by.alex.testing.controller.filter;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter to check user role and {@link User} permission to
 * execute {@link by.alex.testing.controller.command.Command}.
 */
public final class CommandFilter extends BaseFilter {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandFilter.class);

    /**
     * Guest commands.
     *
     * @see by.alex.testing.controller.command.Command
     * @see UserRole
     * @see by.alex.testing.controller.FrontController
     * @see by.alex.testing.controller.command.CommandProvider
     * @see CommandName
     */
    private static final Set<String> GUEST_COMMANDS = initGuestCommands();

    /**
     * Common commands.
     *
     * @see by.alex.testing.controller.command.Command
     * @see UserRole
     * @see by.alex.testing.controller.FrontController
     * @see by.alex.testing.controller.command.CommandProvider
     * @see CommandName
     */
    private static final Set<String> COMMON_COMMANDS = initCommonCommands();

    /**
     * Student commands.
     *
     * @see by.alex.testing.controller.command.Command
     * @see UserRole
     * @see by.alex.testing.controller.FrontController
     * @see by.alex.testing.controller.command.CommandProvider
     * @see CommandName
     */
    private static final Set<String> STUDENT_COMMANDS = initStudentCommands();

    /**
     * Teacher commands.
     *
     * @see by.alex.testing.controller.command.Command
     * @see UserRole
     * @see by.alex.testing.controller.FrontController
     * @see by.alex.testing.controller.command.CommandProvider
     * @see CommandName
     */
    private static final Set<String> TEACHER_COMMANDS = initTeacherCommands();

    /**
     * Administrator commands.
     *
     * @see by.alex.testing.controller.command.Command
     * @see UserRole
     * @see by.alex.testing.controller.FrontController
     * @see by.alex.testing.controller.command.CommandProvider
     * @see CommandName
     */
    private static final Set<String> ADMIN_COMMANDS = initAdminCommands();

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        LOGGER.debug("Command Filter processing");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        String commandName = req.getParameter(RequestConstant.COMMAND);
        if (StringUtils.isNullOrEmpty(commandName)) {
            LOGGER.info("Command not found, redirecting to error page");
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {

            User user = (User) session.getAttribute(RequestConstant.USER);
            UserRole role = user != null ? user.getRole() : UserRole.GUEST;
            LOGGER.info("User role is: {}", role);
            Set<String> accessibleCommands;
            switch (role) {
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
                    accessibleCommands = GUEST_COMMANDS;
            }

            LOGGER.info("{} command received", commandName);
            if (accessibleCommands.contains(commandName)) {
                LOGGER.info("User HAVE permission to '{}' command",
                        commandName);
                chain.doFilter(request, response);
            } else {
                String page;
                LOGGER.info("User DO NOT have permission to '{}' command",
                        commandName);
                if (role.equals(UserRole.GUEST)) {
                    page = req.getContextPath() + req.getServletPath() + "?"
                            + RequestConstant.COMMAND + "="
                            + CommandName.TO_LOGIN_PAGE;
                } else {
                    page = req.getContextPath() + req.getServletPath() + "?"
                            + RequestConstant.COMMAND + "="
                            + CommandName.TO_HOME_PAGE;
                }
                LOGGER.info("Redirecting to: '{}'", page);
                res.sendRedirect(page);
            }
        }
    }

    private static Set<String> initCommonCommands() {
        Set<String> commands = new HashSet<>();
        commands.add(CommandName.CHANGE_LOCALE);
        commands.add(CommandName.LOGOUT);
        commands.add(CommandName.TO_HOME_PAGE);
        commands.add(CommandName.TO_INDEX_PAGE);
        commands.add(CommandName.TO_PROFILE_PAGE);
        commands.add(CommandName.UPDATE_PROFILE);
        return commands;
    }

    private static Set<String> initGuestCommands() {
        Set<String> commands = new HashSet<>();
        commands.add(CommandName.CHANGE_LOCALE);
        commands.add(CommandName.LOGIN);
        commands.add(CommandName.REGISTER);
        commands.add(CommandName.TO_INDEX_PAGE);
        commands.add(CommandName.TO_LOGIN_PAGE);
        commands.add(CommandName.TO_REGISTRATION_PAGE);
        return commands;
    }

    private static Set<String> initStudentCommands() {
        Set<String> commands = new HashSet<>(COMMON_COMMANDS);
        commands.add(CommandName.CANCEL_REQUEST);
        commands.add(CommandName.LEAVE_COURSE);
        commands.add(CommandName.SEND_REQUEST);
        commands.add(CommandName.SHOW_AVAILABLE_COURSES);
        commands.add(CommandName.SHOW_REQUESTED_COURSES);
        commands.add(CommandName.SHOW_STUDENT_COURSES);
        commands.add(CommandName.SHOW_COURSE_LESSONS);
        return commands;
    }

    private static Set<String> initTeacherCommands() {
        Set<String> commands = new HashSet<>(COMMON_COMMANDS);
        commands.add(CommandName.ACCEPT_STUDENT_REQUEST);
        commands.add(CommandName.CREATE_COURSE);
        commands.add(CommandName.CREATE_LESSON);
        commands.add(CommandName.DECLINE_STUDENT_REQUEST);
        commands.add(CommandName.DELETE_COURSE);
        commands.add(CommandName.DELETE_LESSON);
        commands.add(CommandName.DELETE_USER_FROM_COURSE);
        commands.add(CommandName.SHOW_ATTENDANCES);
        commands.add(CommandName.SHOW_COURSE_REQUESTS);
        commands.add(CommandName.SHOW_COURSE_USERS);
        commands.add(CommandName.SHOW_LESSONS);
        commands.add(CommandName.SHOW_TEACHER_COURSES);
        commands.add(CommandName.UPDATE_ATTENDANCE);
        commands.add(CommandName.UPDATE_COURSE);
        commands.add(CommandName.UPDATE_LESSON);
        return commands;
    }

    private static Set<String> initAdminCommands() {
        Set<String> commands = new HashSet<>(COMMON_COMMANDS);
        commands.add(CommandName.CREATE_COURSE_CATEGORY);
        commands.add(CommandName.DELETE_COURSE_CATEGORY);
        commands.add(CommandName.DELETE_COURSE);
        commands.add(CommandName.DELETE_USER);
        commands.add(CommandName.SHOW_COURSE_CATEGORIES);
        commands.add(CommandName.SHOW_COURSES);
        commands.add(CommandName.SHOW_USERS);
        commands.add(CommandName.UPDATE_COURSE_CATEGORY);
        return commands;
    }
}
