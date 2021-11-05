package by.alex.testing.controller.command;

import by.alex.testing.controller.command.impl.*;

import java.util.Map;

public class CommandFactory {

    private static final Map<String, Command> commandMap = initCommandMap();

    private CommandFactory() {
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(
                Map.entry("to_home_page", new ToHomePageCommand()),
                Map.entry("show_course_tests", new ShowCourseTests()),
                Map.entry("show_course_users", new ShowCourseUsers()),
                Map.entry("show_courses", new ShowCourses()),
                Map.entry("remove_user_from_course", new RemoveUserFromCourse()),
                Map.entry("remove_test_from_course", new RemoveTestFromCourse())
        );
    }

    public static Command resolveCommand(String commandName) {

        return commandMap.getOrDefault(commandName, new ToHomePageCommand());
    }
}
