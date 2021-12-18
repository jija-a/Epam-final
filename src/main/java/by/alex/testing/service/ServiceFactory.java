package by.alex.testing.service;

import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.service.impl.AdminServiceImpl;
import by.alex.testing.service.impl.AttendanceServiceImpl;
import by.alex.testing.service.impl.CourseCategoryServiceImpl;
import by.alex.testing.service.impl.CourseServiceImpl;
import by.alex.testing.service.impl.CourseUserServiceImpl;
import by.alex.testing.service.impl.LessonServiceImpl;
import by.alex.testing.service.impl.PaginationServiceImpl;
import by.alex.testing.service.impl.StudentServiceImpl;
import by.alex.testing.service.impl.TeacherServiceImpl;
import by.alex.testing.service.impl.UserServiceImpl;

public final class ServiceFactory {

    /**
     * {@link ServiceFactory} instance. Singleton pattern.
     */
    private static final ServiceFactory FACTORY = new ServiceFactory();

    /**
     * @return {@link ServiceFactory} instance.
     */
    public static ServiceFactory getInstance() {
        return FACTORY;
    }

    private ServiceFactory() {
        TransactionHandler.init();
    }

    /**
     * @return {@link UserService} instance.
     */
    public UserService getCommonService() {
        return UserServiceImpl.getInstance();
    }

    /**
     * @return {@link TeacherService} instance.
     */
    public TeacherService getTeacherService() {
        return TeacherServiceImpl.getInstance();
    }

    /**
     * @return {@link PaginationService} instance.
     */
    public PaginationService getPaginationService() {
        return PaginationServiceImpl.getInstance();
    }

    /**
     * @return {@link StudentService} instance.
     */
    public StudentService getStudentService() {
        return StudentServiceImpl.getInstance();
    }

    /**
     * @return {@link CourseCategoryService} instance.
     */
    public CourseCategoryService getCourseCategoryService() {
        return CourseCategoryServiceImpl.getInstance();
    }

    /**
     * @return {@link AdminService} instance.
     */
    public AdminService getAdminService() {
        return AdminServiceImpl.getInstance();
    }

    /**
     * @return {@link CourseUserService} instance.
     */
    public CourseUserService getCourseUserService() {
        return CourseUserServiceImpl.getInstance();
    }

    /**
     * @return {@link CourseService} instance.
     */
    public CourseService getCourseService() {
        return CourseServiceImpl.getInstance();
    }

    /**
     * @return {@link LessonService} instance.
     */
    public LessonService getLessonService() {
        return LessonServiceImpl.getInstance();
    }

    /**
     * @return {@link AttendanceService} instance.
     */
    public AttendanceService getAttendanceService() {
        return AttendanceServiceImpl.getInstance();
    }
}
