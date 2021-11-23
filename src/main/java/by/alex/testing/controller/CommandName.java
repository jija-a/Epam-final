package by.alex.testing.controller;

public class CommandName {

    public static final String SHOW_TESTS = "show_tests";
    public static final String CREATE_COURSE_CATEGORY = "create_category";
    public static final String DELETE_USER_FROM_COURSE = "delete_user_from_course";
    public static final String SHOW_TEACHER_COURSES = "show_teacher_courses";
    public static final String CREATE_COURSE = "create_course";
    public static final String UPDATE_COURSE = "update_course";
    public static final String ADD_STUDENT_ON_COURSE = "add_on_course";
    public static final String REQUEST_SIGN_ON_COURSE = "request_sign_on_course";
    public static final String SHOW_COURSE_REQUESTS = "show_requests";
    public static final String ACCEPT_STUDENT_ON_COURSE = "accept_request";
    public static final String DECLINE_REQUEST = "decline_request";
    public static final String UPDATE_TEST_INFO = "update_test";
    public static final String DELETE_TEST = "delete_test";

    private CommandName(){
    }
    
    //common
    public static final String CHANGE_LOCALE = "change_locale";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String REGISTER = "register";
    public static final String SHOW_COURSES = "show_courses";
    public static final String SHOW_COURSE_USERS = "show_course_users";
    public static final String SHOW_USERS = "show_users";
    public static final String TO_PROFILE_PAGE = "to_profile_page";
    public static final String UPDATE_PROFILE = "update_profile";

    //page
    public static final String TO_COURSE_CREATION_PAGE = "to_course_creation_page";
    public static final String TO_COURSE_INFO_PAGE = "to_course_info_page";
    public static final String TO_COURSE_USERS_PAGE = "to_course_users_page";
    public static final String TO_HOME_PAGE = "to_home_page";
    public static final String TO_INDEX_PAGE = "to_index_page";
    public static final String TO_LOGIN_PAGE = "to_login_page";
    public static final String TO_REGISTRATION_PAGE = "to_registration_page";
    public static final String TO_TEST_CREATION_PAGE = "to_test_creation_page";
    public static final String TO_TEST_INFO_PAGE = "to_test_info_page";
    public static final String TO_TEST_RESULTS_PAGE = "to_test_results_page";
    public static final String TO_TEST_UPDATE_PAGE = "to_test_update_page";

    //admin
    public static final String DELETE_COURSE_CATEGORY = "delete_category";
    public static final String DELETE_COURSE = "delete_course";
    public static final String DELETE_USER = "delete_user";
    public static final String SHOW_COURSE_CATEGORIES = "show_course_categories";
    public static final String UPDATE_COURSE_CATEGORY = "update_category";
}
