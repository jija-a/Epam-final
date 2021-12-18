package by.alex.testing.dao;

import by.alex.testing.dao.mysql.MySqlDaoFactory;
import by.alex.testing.domain.UnknownEntityException;

public abstract class DaoFactory {

    public enum DaoType {
        /**
         * MySQL DB type.
         */
        MYSQL;
    }

    /**
     * @return {@link CourseCategoryDao} implementation
     */
    public abstract CourseCategoryDao getCourseCategoryDao();

    /**
     * @return {@link CourseDao} implementation
     */
    public abstract CourseDao getCourseDao();

    /**
     * @return {@link CourseUserDao} implementation
     */
    public abstract CourseUserDao getCourseUserDao();

    /**
     * @return {@link UserDao} implementation
     */
    public abstract UserDao getUserDao();

    /**
     * @return {@link LessonDao} implementation
     */
    public abstract LessonDao getLessonDao();

    /**
     * @return {@link AttendanceDao} implementation
     */
    public abstract AttendanceDao getAttendanceDao();

    /**
     * @param type Database type
     * @return concrete {@link DaoFactory}
     * @throws UnknownEntityException if it can't resolve database type
     */
    public static DaoFactory getDaoFactory(final DaoType type)
            throws UnknownEntityException {
        if (type == DaoType.MYSQL) {
            return MySqlDaoFactory.getInstance();
        }
        throw new UnknownEntityException("Wrong DAO type");
    }

}
