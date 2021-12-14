package by.alex.testing.dao;

import by.alex.testing.dao.mysql.*;

public abstract class DaoFactory {

    public enum DaoType{
        MYSQL;
    }

    public abstract CourseCategoryDaoImpl getCourseCategoryDao();
    public abstract CourseDaoImpl getCourseDao();
    public abstract CourseUserDaoImpl getCourseUserDao();
    public abstract UserDaoImpl getUserDao();
    public abstract LessonDaoImpl getLessonDao();
    public abstract AttendanceDaoImpl getAttendanceDao();

    public static DaoFactory getDaoFactory(DaoType type) throws RuntimeException {
        if (type == DaoType.MYSQL) {
            return MySqlDaoFactory.getInstance();
        }
        throw new RuntimeException("Wrong DAO type");
    }

}
