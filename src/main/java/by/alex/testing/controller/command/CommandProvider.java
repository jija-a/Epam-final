package by.alex.testing.controller.command;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.command.impl.admin.*;
import by.alex.testing.controller.command.impl.common.*;
import by.alex.testing.controller.command.impl.student.*;
import by.alex.testing.controller.command.impl.teacher.*;

import java.util.Map;

public class CommandProvider {

    private static final Map<String, Command> commandMap = initCommandMap();

    private CommandProvider() {
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(

                //direction
                Map.entry(CommandName.TO_HOME_PAGE, new ToHomePageCommand()),
                Map.entry(CommandName.TO_INDEX_PAGE, new ToIndexPageCommand()),
                Map.entry(CommandName.TO_LOGIN_PAGE, new ToLoginPageCommand()),
                Map.entry(CommandName.TO_REGISTRATION_PAGE, new ToRegistrationPageCommand()),

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

                //student
                Map.entry(CommandName.CANCEL_REQUEST, new CancelRequestCommand()),
                Map.entry(CommandName.LEAVE_COURSE, new LeaveCourseCommand()),
                Map.entry(CommandName.SEND_REQUEST, new SendRequestCommand()),
                Map.entry(CommandName.SHOW_AVAILABLE_COURSES, new ShowAvailableCoursesCommand()),
                Map.entry(CommandName.SHOW_COURSE_LESSONS, new ShowStudentLessonsCommand()),
                Map.entry(CommandName.SHOW_STUDENT_COURSES, new ShowStudentCoursesCommand()),
                Map.entry(CommandName.SHOW_REQUESTED_COURSES, new ShowRequestedCoursesCommand()),

                //teacher
                Map.entry(CommandName.ACCEPT_STUDENT_REQUEST, new AcceptStudentRequestCommand()),
                Map.entry(CommandName.CREATE_COURSE, new CreateCourseCommand()),
                Map.entry(CommandName.CREATE_LESSON, new CreateLessonCommand()),
                Map.entry(CommandName.DECLINE_STUDENT_REQUEST, new DeclineStudentRequestCommand()),
                Map.entry(CommandName.DELETE_LESSON, new DeleteLessonCommand()),
                Map.entry(CommandName.DELETE_USER_FROM_COURSE, new DeleteUserFromCourseCommand()),
                Map.entry(CommandName.SHOW_COURSE_REQUESTS, new ShowCourseRequestsCommand()),
                Map.entry(CommandName.SHOW_TEACHER_COURSES, new ShowTeacherCoursesCommand()),
                Map.entry(CommandName.UPDATE_COURSE, new UpdateCourseCommand()),
                Map.entry(CommandName.SHOW_LESSONS, new ShowLessonsCommand()),
                Map.entry(CommandName.SHOW_ATTENDANCES, new ShowAttendanceCommand()),
                Map.entry(CommandName.UPDATE_ATTENDANCE, new UpdateAttendanceCommand()),
                Map.entry(CommandName.UPDATE_LESSON, new UpdateLessonCommand()),


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
