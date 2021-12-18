package by.alex.testing.controller.command;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.command.impl.admin.CreateCourseCategoryCommand;
import by.alex.testing.controller.command.impl.admin.DeleteCourseCategoryCommand;
import by.alex.testing.controller.command.impl.admin.DeleteUserCommand;
import by.alex.testing.controller.command.impl.admin.ShowCourseCategoriesCommand;
import by.alex.testing.controller.command.impl.admin.ShowCoursesCommand;
import by.alex.testing.controller.command.impl.admin.ShowUsersCommand;
import by.alex.testing.controller.command.impl.admin.UpdateCategoryCommand;
import by.alex.testing.controller.command.impl.common.ChangeLocaleCommand;
import by.alex.testing.controller.command.impl.common.LogInCommand;
import by.alex.testing.controller.command.impl.common.LogoutCommand;
import by.alex.testing.controller.command.impl.common.RegistrationCommand;
import by.alex.testing.controller.command.impl.common.ToHomePageCommand;
import by.alex.testing.controller.command.impl.common.ToIndexPageCommand;
import by.alex.testing.controller.command.impl.common.ToLoginPageCommand;
import by.alex.testing.controller.command.impl.common.ToProfilePageCommand;
import by.alex.testing.controller.command.impl.common.ToRegistrationPageCommand;
import by.alex.testing.controller.command.impl.common.UpdateProfileCommand;
import by.alex.testing.controller.command.impl.student.CancelRequestCommand;
import by.alex.testing.controller.command.impl.student.LeaveCourseCommand;
import by.alex.testing.controller.command.impl.student.SendRequestCommand;
import by.alex.testing.controller.command.impl.student.ShowAvailableCoursesCommand;
import by.alex.testing.controller.command.impl.student.ShowRequestedCoursesCommand;
import by.alex.testing.controller.command.impl.student.ShowStudentCoursesCommand;
import by.alex.testing.controller.command.impl.student.ShowStudentLessonsCommand;
import by.alex.testing.controller.command.impl.teacher.AcceptStudentRequestCommand;
import by.alex.testing.controller.command.impl.teacher.CreateCourseCommand;
import by.alex.testing.controller.command.impl.teacher.CreateLessonCommand;
import by.alex.testing.controller.command.impl.teacher.DeclineStudentRequestCommand;
import by.alex.testing.controller.command.impl.teacher.DeleteCourseCommand;
import by.alex.testing.controller.command.impl.teacher.DeleteLessonCommand;
import by.alex.testing.controller.command.impl.teacher.DeleteUserFromCourseCommand;
import by.alex.testing.controller.command.impl.teacher.ShowAttendanceCommand;
import by.alex.testing.controller.command.impl.teacher.ShowCourseRequestsCommand;
import by.alex.testing.controller.command.impl.teacher.ShowCourseUsersCommand;
import by.alex.testing.controller.command.impl.teacher.ShowLessonsCommand;
import by.alex.testing.controller.command.impl.teacher.ShowTeacherCoursesCommand;
import by.alex.testing.controller.command.impl.teacher.UpdateAttendanceCommand;
import by.alex.testing.controller.command.impl.teacher.UpdateCourseCommand;
import by.alex.testing.controller.command.impl.teacher.UpdateLessonCommand;

import java.util.Map;

/**
 * Class contains Map of {@link CommandName} as a key and
 * {@link Command} as a value. Has method that gets {@link Command}
 * from map and returns it.
 */
public final class CommandProvider {

    /**
     * Command map of {@link CommandName} as a key and
     * {@link Command} as a value.
     */
    private static final Map<String, Command> COMMAND_MAP = initCommandMap();

    private CommandProvider() {
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(
                Map.entry(CommandName.TO_HOME_PAGE, new ToHomePageCommand()),
                Map.entry(CommandName.TO_INDEX_PAGE, new ToIndexPageCommand()),
                Map.entry(CommandName.TO_LOGIN_PAGE, new ToLoginPageCommand()),
                Map.entry(CommandName.TO_REGISTRATION_PAGE,
                        new ToRegistrationPageCommand()),
                Map.entry(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand()),
                Map.entry(CommandName.LOGIN, new LogInCommand()),
                Map.entry(CommandName.LOGOUT, new LogoutCommand()),
                Map.entry(CommandName.REGISTER, new RegistrationCommand()),
                Map.entry(CommandName.SHOW_COURSES, new ShowCoursesCommand()),
                Map.entry(CommandName.SHOW_COURSE_USERS,
                        new ShowCourseUsersCommand()),
                Map.entry(CommandName.SHOW_USERS, new ShowUsersCommand()),
                Map.entry(CommandName.TO_PROFILE_PAGE,
                        new ToProfilePageCommand()),
                Map.entry(CommandName.UPDATE_PROFILE,
                        new UpdateProfileCommand()),
                Map.entry(CommandName.CANCEL_REQUEST,
                        new CancelRequestCommand()),
                Map.entry(CommandName.LEAVE_COURSE, new LeaveCourseCommand()),
                Map.entry(CommandName.SEND_REQUEST, new SendRequestCommand()),
                Map.entry(CommandName.SHOW_AVAILABLE_COURSES,
                        new ShowAvailableCoursesCommand()),
                Map.entry(CommandName.SHOW_COURSE_LESSONS,
                        new ShowStudentLessonsCommand()),
                Map.entry(CommandName.SHOW_STUDENT_COURSES,
                        new ShowStudentCoursesCommand()),
                Map.entry(CommandName.SHOW_REQUESTED_COURSES,
                        new ShowRequestedCoursesCommand()),
                Map.entry(CommandName.ACCEPT_STUDENT_REQUEST,
                        new AcceptStudentRequestCommand()),
                Map.entry(CommandName.CREATE_COURSE, new CreateCourseCommand()),
                Map.entry(CommandName.CREATE_LESSON, new CreateLessonCommand()),
                Map.entry(CommandName.DECLINE_STUDENT_REQUEST,
                        new DeclineStudentRequestCommand()),
                Map.entry(CommandName.DELETE_LESSON, new DeleteLessonCommand()),
                Map.entry(CommandName.DELETE_USER_FROM_COURSE,
                        new DeleteUserFromCourseCommand()),
                Map.entry(CommandName.SHOW_COURSE_REQUESTS,
                        new ShowCourseRequestsCommand()),
                Map.entry(CommandName.SHOW_TEACHER_COURSES,
                        new ShowTeacherCoursesCommand()),
                Map.entry(CommandName.UPDATE_COURSE, new UpdateCourseCommand()),
                Map.entry(CommandName.SHOW_LESSONS, new ShowLessonsCommand()),
                Map.entry(CommandName.SHOW_ATTENDANCES,
                        new ShowAttendanceCommand()),
                Map.entry(CommandName.UPDATE_ATTENDANCE,
                        new UpdateAttendanceCommand()),
                Map.entry(CommandName.UPDATE_LESSON, new UpdateLessonCommand()),
                Map.entry(CommandName.CREATE_COURSE_CATEGORY,
                        new CreateCourseCategoryCommand()),
                Map.entry(CommandName.DELETE_COURSE_CATEGORY,
                        new DeleteCourseCategoryCommand()),
                Map.entry(CommandName.DELETE_COURSE, new DeleteCourseCommand()),
                Map.entry(CommandName.DELETE_USER, new DeleteUserCommand()),
                Map.entry(CommandName.SHOW_COURSE_CATEGORIES,
                        new ShowCourseCategoriesCommand()),
                Map.entry(CommandName.UPDATE_COURSE_CATEGORY,
                        new UpdateCategoryCommand())
        );
    }

    /**
     * Method resolves {@link Command} from Command map by it key.
     *
     * @param commandName {@link String} key in map
     * @return {@link Command} that was resolved or
     * {@link ToHomePageCommand} as default value
     */
    public static Command resolveCommand(final String commandName) {

        return COMMAND_MAP.getOrDefault(commandName, new ToHomePageCommand());
    }
}
