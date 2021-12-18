package by.alex.testing.controller;

/**
 * Class represents all {@link by.alex.testing.controller.command.Command}
 * names as constants.
 */
public final class CommandName {

    private CommandName() {
    }

    /**
     * Guest command name.
     * The name that denote login user command.
     */
    public static final String LOGIN = "login";

    /**
     * Guest command name.
     * The name that denote register user command.
     */
    public static final String REGISTER = "register";

    /**
     * Common command name.
     * The name that denote change locale command.
     */
    public static final String CHANGE_LOCALE = "change_locale";

    /**
     * Common command name.
     * The name that denote logout user command.
     */
    public static final String LOGOUT = "logout";

    /**
     * Common command name.
     * The name that denote show courses list command.
     */
    public static final String SHOW_COURSES = "show_courses";

    /**
     * Common command name.
     * The name that denote show users list command.
     */
    public static final String SHOW_USERS = "show_users";

    /**
     * Direction command name.
     * The name of the command that forward to profile page.
     */
    public static final String TO_PROFILE_PAGE = "to_profile_page";

    /**
     * Direction command name.
     * The name that marks update profile command.
     */
    public static final String UPDATE_PROFILE = "update_profile";

    /**
     * Direction command name.
     * The name of the command that forward to home page.
     */
    public static final String TO_HOME_PAGE = "to_home_page";

    /**
     * Direction command name.
     * The name of the command that forward to index page.
     */
    public static final String TO_INDEX_PAGE = "to_index_page";

    /**
     * Direction command name.
     * The name of the command that forward to login page.
     */
    public static final String TO_LOGIN_PAGE = "to_login_page";

    /**
     * Direction command name.
     * The name of the command that forward to registration page.
     */
    public static final String TO_REGISTRATION_PAGE = "to_registration_page";

    /**
     * Administrator command name.
     * The name that denote show users list command.
     */
    public static final String CREATE_COURSE_CATEGORY = "create_category";

    /**
     * Administrator command name.
     * The name that denote delete course category command.
     */
    public static final String DELETE_COURSE_CATEGORY = "delete_category";

    /**
     * Administrator command name.
     * The name that denote delete user command.
     */
    public static final String DELETE_USER = "delete_user";

    /**
     * Administrator command name.
     * The name that denote show course categories command.
     */
    public static final String SHOW_COURSE_CATEGORIES =
            "show_course_categories";

    /**
     * Administrator command name.
     * The name that denote update course category command.
     */
    public static final String UPDATE_COURSE_CATEGORY = "update_category";

    /**
     * Teacher command name.
     * The name that denote accept student request on course command.
     */
    public static final String ACCEPT_STUDENT_REQUEST = "accept_request";

    /**
     * Teacher command name.
     * The name that denote create course command.
     */
    public static final String CREATE_COURSE = "create_course";

    /**
     * Teacher command name.
     * The name that denote create lesson command.
     */
    public static final String CREATE_LESSON = "create_lesson";

    /**
     * Teacher command name.
     * The name that denote decline student request on course command.
     */
    public static final String DECLINE_STUDENT_REQUEST = "decline_request";

    /**
     * Teacher command name.
     * The name that denote delete lesson command.
     */
    public static final String DELETE_LESSON = "delete_lesson";

    /**
     * Teacher command name.
     * The name that denote delete course command.
     */
    public static final String DELETE_COURSE = "delete_course";

    /**
     * Teacher command name.
     * The name that denote delete user from course command.
     */
    public static final String DELETE_USER_FROM_COURSE =
            "delete_user_from_course";

    /**
     * Teacher command name.
     * The name that denote show attendances command.
     */
    public static final String SHOW_ATTENDANCES = "show_marks";

    /**
     * Teacher command name.
     * The name that denote show course requests command.
     */
    public static final String SHOW_COURSE_REQUESTS = "show_requests";

    /**
     * Teacher command name.
     * The name that denote show course users command.
     */
    public static final String SHOW_COURSE_USERS = "show_course_users";

    /**
     * Teacher and Student command name.
     * The name that denote show lessons command.
     */
    public static final String SHOW_LESSONS = "show_lessons";

    /**
     * Teacher command name.
     * The name that denote show teacher courses command.
     */
    public static final String SHOW_TEACHER_COURSES = "show_teacher_courses";

    /**
     * Teacher command name.
     * The name that denote update attendance command.
     */
    public static final String UPDATE_ATTENDANCE = "update_attendance";

    /**
     * Teacher command name.
     * The name that denote update course command.
     */
    public static final String UPDATE_COURSE = "update_course";

    /**
     * Teacher command name.
     * The name that denote update lesson command.
     */
    public static final String UPDATE_LESSON = "update_lesson";

    /**
     * Student command name.
     * The name that denote cancel request on course command.
     */
    public static final String CANCEL_REQUEST = "cancel_request";

    /**
     * Student command name.
     * The name that denote leave course command.
     */
    public static final String LEAVE_COURSE = "leave_course";

    /**
     * Student command name.
     * The name that denote send request on course command.
     */
    public static final String SEND_REQUEST = "send_request";

    /**
     * Student command name.
     * The name that denote show courses that user does not enter command.
     */
    public static final String SHOW_AVAILABLE_COURSES =
            "show_available_courses";

    /**
     * Student command name.
     * The name that denote show course lessons command.
     */
    public static final String SHOW_COURSE_LESSONS = "show_course_lessons";

    /**
     * Student command name.
     * The name that denote show student courses command.
     */
    public static final String SHOW_STUDENT_COURSES = "show_student_courses";

    /**
     * Student command name.
     * The name that denote show requested courses command.
     */
    public static final String SHOW_REQUESTED_COURSES =
            "show_requested_courses";
}
