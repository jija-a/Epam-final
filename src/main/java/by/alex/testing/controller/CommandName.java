package by.alex.testing.controller;

public class CommandName {

    private CommandName() {
    }

    //common
    public static final String CHANGE_LOCALE = "change_locale";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String REGISTER = "register";
    public static final String SHOW_COURSES = "show_courses";
    public static final String SHOW_USERS = "show_users";
    public static final String TO_PROFILE_PAGE = "to_profile_page";
    public static final String UPDATE_PROFILE = "update_profile";

    //page
    public static final String TO_HOME_PAGE = "to_home_page";
    public static final String TO_INDEX_PAGE = "to_index_page";
    public static final String TO_LOGIN_PAGE = "to_login_page";
    public static final String TO_REGISTRATION_PAGE = "to_registration_page";

    //admin
    public static final String CREATE_COURSE_CATEGORY = "create_category";
    public static final String DELETE_COURSE_CATEGORY = "delete_category";
    public static final String DELETE_USER = "delete_user";
    public static final String SHOW_COURSE_CATEGORIES = "show_course_categories";
    public static final String UPDATE_COURSE_CATEGORY = "update_category";

    //teacher
    public static final String ACCEPT_STUDENT_REQUEST = "accept_request";
    public static final String CREATE_COURSE = "create_course";
    public static final String CREATE_LESSON = "create_lesson";
    public static final String DECLINE_STUDENT_REQUEST = "decline_request";
    public static final String DELETE_LESSON = "delete_lesson";
    public static final String DELETE_COURSE = "delete_course";
    public static final String DELETE_USER_FROM_COURSE = "delete_user_from_course";
    public static final String SHOW_ATTENDANCES = "show_marks";
    public static final String SHOW_COURSE_REQUESTS = "show_requests";
    public static final String SHOW_COURSE_USERS = "show_course_users";
    public static final String SHOW_LESSONS = "show_lessons";
    public static final String SHOW_TEACHER_COURSES = "show_teacher_courses";
    public static final String UPDATE_ATTENDANCE = "update_attendance";
    public static final String UPDATE_COURSE = "update_course";
    public static final String UPDATE_LESSON = "update_lesson";

    //student
    public static final String CANCEL_REQUEST = "cancel_request";
    public static final String LEAVE_COURSE = "leave_course";
    public static final String SEND_REQUEST = "send_request";
    public static final String SHOW_AVAILABLE_COURSES = "show_available_courses";
    public static final String SHOW_COURSE_LESSONS = "show_course_lessons";
    public static final String SHOW_STUDENT_COURSES = "show_student_courses";
    public static final String SHOW_REQUESTED_COURSES = "show_requested_courses";
}
