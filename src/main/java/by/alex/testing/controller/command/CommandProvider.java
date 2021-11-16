package by.alex.testing.controller.command;

import by.alex.testing.controller.command.impl.*;
import by.alex.testing.controller.command.impl.direct.*;
import by.alex.testing.controller.command.impl.student.ShowUserCoursesCommand;
import by.alex.testing.controller.command.impl.student.ShowUserResultsCommand;
import by.alex.testing.controller.command.impl.student.SignOnCourseCommand;
import by.alex.testing.controller.command.impl.teacher.*;

import java.util.Map;

public class CommandProvider {

    private static final Map<String, Command> commandMap = initCommandMap();

    private CommandProvider() {
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(
                //direction
                Map.entry("to_add_users_on_course_page", new ToAddUsersOnCoursePageCommand()),
                Map.entry("to_create_test_page", new ToCreateTestPageCommand()),
                Map.entry("to_home_page", new ToHomePageCommand()),
                Map.entry("to_login_page", new ToLoginPageCommand()),
                Map.entry("to_register_page", new ToRegisterPageCommand()),
                Map.entry("to_update_course_info_page", new ToUpdateCourseInfoPageCommand()),

                //student
                Map.entry("show_user_courses", new ShowUserCoursesCommand()),
                Map.entry("show_user_results", new ShowUserResultsCommand()),
                Map.entry("sign_on_course", new SignOnCourseCommand()),

                //teacher
                Map.entry("show_teacher_courses", new ShowTeacherCourses()),
                Map.entry("update_course", new UpdateCourseCommand()),

                //common
                Map.entry("change_locale", new ChangeLocaleCommand()),
                Map.entry("login", new LogInCommand()),
                Map.entry("logout", new LogoutCommand()),
                Map.entry("register", new RegisterCommand()),
                Map.entry("show_courses", new ShowCourses()),
                Map.entry("show_course_tests", new ShowCourseTests()),
                Map.entry("show_course_users", new ShowCourseUsers())
        );
    }

    public static Command resolveCommand(String commandName) {

        return commandMap.getOrDefault(commandName, new ToHomePageCommand());
    }
}
