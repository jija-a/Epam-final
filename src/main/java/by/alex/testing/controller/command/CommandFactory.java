package by.alex.testing.controller.command;

import by.alex.testing.controller.command.impl.*;
import by.alex.testing.controller.command.impl.direct.*;
import by.alex.testing.controller.command.impl.student.ShowUserCoursesCommand;
import by.alex.testing.controller.command.impl.student.SignOnCourseCommand;
import by.alex.testing.controller.command.impl.teacher.CreateTestCommand;
import by.alex.testing.controller.command.impl.teacher.RemoveTestFromCourse;
import by.alex.testing.controller.command.impl.teacher.RemoveUserFromCourse;
import by.alex.testing.controller.command.impl.teacher.UpdateCourseCommand;

import java.util.Map;

public class CommandFactory {

    private static final Map<String, Command> commandMap = initCommandMap();

    private CommandFactory() {
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(
                Map.entry("to_home_page", new ToHomePageCommand()),
                Map.entry("to_login_page", new ToLoginPageCommand()),
                Map.entry("to_register_page", new ToRegisterPageCommand()),
                Map.entry("to_add_users_on_course_page", new ToAddUsersOnCoursePageCommand()),
                Map.entry("to_create_test_page", new ToCreateTestPageCommand()),
                Map.entry("to_update_course_info_page", new ToUpdateCourseInfoPageCommand()),

                Map.entry("create_test", new CreateTestCommand()),
                Map.entry("login", new LogInCommand()),
                Map.entry("logout", new LogoutCommand()),
                Map.entry("register_user", new RegisterCommand()),
                Map.entry("remove_test_from_course", new RemoveTestFromCourse()),
                Map.entry("remove_user_from_course", new RemoveUserFromCourse()),
                Map.entry("show_courses", new ShowCourses()),
                Map.entry("show_course_tests", new ShowCourseTests()),
                Map.entry("show_course_users", new ShowCourseUsers()),
                Map.entry("show_user_courses", new ShowUserCoursesCommand()),
                Map.entry("sign_on_course", new SignOnCourseCommand()),
                Map.entry("update_course", new UpdateCourseCommand())
        );
    }

    public static Command resolveCommand(String commandName) {

        return commandMap.getOrDefault(commandName, new ToHomePageCommand());
    }
}
