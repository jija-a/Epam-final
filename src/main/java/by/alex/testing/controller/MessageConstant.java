package by.alex.testing.controller;

/**
 * Class represents constant from resource locale files.
 */
public final class MessageConstant {

    private MessageConstant() {
    }

    /**
     * Footer localized text message.
     */
    public static final String COPYRIGHT = "footer.p.copyright";

    /**
     * Error message. If user enter wrong conf password.
     */
    public static final String CONFIRMATION_PSW_ERROR = "error.conf_psw";

    /**
     * Error message. If user enter wrong login or password.
     */
    public static final String LOGIN_ERROR = "error.login";

    /**
     * Error message. If user enter wrong login.
     */
    public static final String LOGIN_REG_ERROR = "error.registration.login";

    /**
     * Error message. If login that enter user already exists.
     */
    public static final String LOGIN_IS_TAKEN_ERROR =
            "error.registration.login.taken";

    /**
     * Error message. If user enter wrong password.
     */
    public static final String PSW_REG_ERROR = "error.registration.password";

    /**
     * Success message. If user registered successful.
     */
    public static final String REGISTRATION_SUCCESS = "success.registration";

    /**
     * Error message. If user tries to delete administrator.
     */
    public static final String CANT_DELETE_ADMIN = "error.cant.delete.admin";

    /**
     * Success message. If user update entity successful.
     */
    public static final String UPDATED_SUCCESS = "success.update";

    /**
     * Success message. If user delte entity successful.
     */
    public static final String DELETED = "success.deleted";

    /**
     * Success message. If user create entity successful.
     */
    public static final String CREATE_SUCCESS = "success.create";

    /**
     * Error message. If user enter name that already exists.
     */
    public static final String ALREADY_EXISTS = "error.exists";

    /**
     * Error message. If user can't update entity.
     */
    public static final String UPDATE_ERROR = "error.update";

    /**
     * Error message. If user can't update entity.
     */
    public static final String DECLINE_SUCCESS = "success.decline";

    /**
     * Success message. If user accept user on course successful.
     */
    public static final String ACCEPT_SUCCESS = "success.accept";

    /**
     * Error message. If user enter wrong attendance mark data.
     */
    public static final String MARK_ERROR = "error.present.mark";

    /**
     * Error message. If user send wrong request parameters.
     */
    public static final String WRONG_PARAMETERS = "error.parameters";

    /**
     * Error message. If user can't accept user on course.
     */
    public static final String CANT_ACCEPT = "error.accept";

    /**
     * Error message. If user can't create entity.
     */
    public static final String CREATE_ERROR = "error.create";

    /**
     * Error message. If user can't delete entity.
     */
    public static final String DELETE_ERROR = "error.delete";

    /**
     * Error message. If user tries to access unavailable entity.
     */
    public static final String ACCESS_DENIED = "error.access.denied";

    /**
     * Success message. If user sign on course successful.
     */
    public static final String SIGNED = "success.signed";

    /**
     * Error message. If user can't sign on course.
     */
    public static final String CANT_SIGN = "error.signed";

    /**
     * Success message. If user unsigned from course successful.
     */
    public static final String UNSIGNED = "success.unsigned";

    /**
     * Error message. If user can't un sign from course.
     */
    public static final String CANT_UNSIGN = "error.unsigned";

    /**
     * Error message. If user input wrong course title.
     */
    public static final String COURSE_TITLE_ERROR = "error.course.title";

    /**
     * Error message. If user input wrong category title.
     */
    public static final String CATEGORY_TITLE_ERROR = "error.category.title";

    /**
     * Error message. If user input wrong lesson title.
     */
    public static final String LESSON_TITLE_ERROR = "error.lesson.title";

    /**
     * Error message. If user input wrong name.
     */
    public static final String USER_NAME_ERROR = "error.user.name";

    /**
     * Error message. If user input wrong dates.
     */
    public static final String DATE_ERROR = "error.date";
}
