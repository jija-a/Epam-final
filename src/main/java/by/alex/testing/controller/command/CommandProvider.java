package by.alex.testing.controller.command;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.command.impl.admin.DeleteCourseCategoryCommand;
import by.alex.testing.controller.command.impl.admin.DeleteCourseCommand;
import by.alex.testing.controller.command.impl.admin.DeleteUserCommand;
import by.alex.testing.controller.command.impl.admin.ShowCourseCategoriesCommand;
import by.alex.testing.controller.command.impl.common.*;
import by.alex.testing.controller.command.impl.page.*;

import java.util.Map;

public class CommandProvider {

    private static final Map<String, Command> commandMap = initCommandMap();

    private CommandProvider() {
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(

                //direction
                Map.entry(CommandName.TO_COURSE_CREATION_PAGE, new ToCourseCreationPageCommand()),
                Map.entry(CommandName.TO_COURSE_INFO_PAGE, new ToCourseInfoPageCommand()),
                Map.entry(CommandName.TO_COURSE_USERS_PAGE, new ToCourseUsersPageCommand()),
                Map.entry(CommandName.TO_HOME_PAGE, new ToHomePageCommand()),
                Map.entry(CommandName.TO_INDEX_PAGE, new ToIndexPageCommand()),
                Map.entry(CommandName.TO_LOGIN_PAGE, new ToLoginPageCommand()),
                Map.entry(CommandName.TO_REGISTRATION_PAGE, new ToRegistrationPageCommand()),
                Map.entry(CommandName.TO_TEST_CREATION_PAGE, new ToTestCreationPageCommand()),
                Map.entry(CommandName.TO_TEST_INFO_PAGE, new ToTestInfoPageCommand()),
                Map.entry(CommandName.TO_TEST_RESULTS_PAGE, new ToTestResultsPageCommand()),
                Map.entry(CommandName.TO_TEST_UPDATE_PAGE, new ToTestUpdatePageCommand()),

                //common
                Map.entry(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand()),
                Map.entry(CommandName.LOGIN, new LogInCommand()),
                Map.entry(CommandName.LOGOUT, new LogoutCommand()),
                Map.entry(CommandName.REGISTER, new RegistrationCommand()),
                Map.entry(CommandName.SHOW_COURSES, new ShowCoursesCommand()),
                Map.entry(CommandName.SHOW_USERS, new ShowUsersCommand()),
                Map.entry(CommandName.TO_PROFILE_PAGE, new ToProfilePageCommand()),
                Map.entry(CommandName.UPDATE_PROFILE, new UpdateProfileCommand()),

                //student

                //teacher

                //admin
                Map.entry(CommandName.DELETE_COURSE_CATEGORY, new DeleteCourseCategoryCommand()),
                Map.entry(CommandName.DELETE_COURSE, new DeleteCourseCommand()),
                Map.entry(CommandName.DELETE_USER, new DeleteUserCommand()),
                Map.entry(CommandName.SHOW_COURSE_CATEGORIES, new ShowCourseCategoriesCommand())
                );
    }

    public static Command resolveCommand(String commandName) {

        return commandMap.getOrDefault(commandName, new ToHomePageCommand());
    }
}
