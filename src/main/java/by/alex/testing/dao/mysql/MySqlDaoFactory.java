package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoFactory;

public class MySqlDaoFactory extends DaoFactory {

    private static final MySqlDaoFactory instance = new MySqlDaoFactory();

    public static MySqlDaoFactory getInstance() {
        return instance;
    }

    @Override
    public CourseCategoryDaoImpl getCourseCategoryDao() {
        return new CourseCategoryDaoImpl();
    }

    @Override
    public CourseDaoImpl getCourseDao() {
        return new CourseDaoImpl();
    }

    @Override
    public CourseUserDaoImpl getCourseUserDao() {
        return new CourseUserDaoImpl();
    }

    @Override
    public UserDaoImpl getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public LessonDaoImpl getLessonDao() {
        return new LessonDaoImpl();
    }

    @Override
    public AttendanceDaoImpl getAttendanceDao() {
        return new AttendanceDaoImpl();
    }

}
