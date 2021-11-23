package by.alex.testing.controller.command;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.command.impl.admin.*;
import by.alex.testing.controller.command.impl.common.*;
import by.alex.testing.controller.command.impl.page.*;
import by.alex.testing.controller.command.impl.student.RequestSignOnCourseCommand;
import by.alex.testing.controller.command.impl.teacher.*;

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
                Map.entry(CommandName.SHOW_COURSE_USERS, new ShowCourseUsersCommand()),
                Map.entry(CommandName.SHOW_USERS, new ShowUsersCommand()),
                Map.entry(CommandName.TO_PROFILE_PAGE, new ToProfilePageCommand()),
                Map.entry(CommandName.UPDATE_PROFILE, new UpdateProfileCommand()),
                Map.entry(CommandName.SHOW_TESTS, new ShowCourseTestsCommand()),

                //student
                Map.entry(CommandName.REQUEST_SIGN_ON_COURSE, new RequestSignOnCourseCommand()),

                //teacher
                Map.entry(CommandName.ACCEPT_STUDENT_ON_COURSE, new AcceptStudentRequestCommand()),
                Map.entry(CommandName.CREATE_COURSE, new CreateCourseCommand()),
                Map.entry(CommandName.DECLINE_REQUEST, new DeclineStudentRequestCommand()),
                Map.entry(CommandName.DELETE_TEST, new DeleteTestCommand()),
                Map.entry(CommandName.DELETE_USER_FROM_COURSE, new DeleteUserFromCourseCommand()),
                Map.entry(CommandName.SHOW_COURSE_REQUESTS, new ShowCourseRequestsCommand()),
                Map.entry(CommandName.SHOW_TEACHER_COURSES, new ShowTeacherCoursesCommand()),
                Map.entry(CommandName.UPDATE_COURSE, new UpdateCourseCommand()),
                Map.entry(CommandName.UPDATE_TEST_INFO, new UpdateTestInfoCommand()),

                //admin
                Map.entry(CommandName.CREATE_COURSE_CATEGORY, new CreateCourseCategoryCommand()),
                Map.entry(CommandName.DELETE_COURSE_CATEGORY, new DeleteCourseCategoryCommand()),
                Map.entry(CommandName.DELETE_COURSE, new DeleteCourseCommand()),
                Map.entry(CommandName.DELETE_USER, new DeleteUserCommand()),
                Map.entry(CommandName.SHOW_COURSE_CATEGORIES, new ShowCourseCategoriesCommand()),
                Map.entry(CommandName.UPDATE_COURSE_CATEGORY, new UpdateCategoryCommand())
        );
    }

    public static Command resolveCommand(String commandName) {

        return commandMap.getOrDefault(commandName, new ToHomePageCommand());
    }
}
