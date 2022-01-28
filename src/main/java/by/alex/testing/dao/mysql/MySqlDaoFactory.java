package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoFactory;

public class MySqlDaoFactory extends DaoFactory {

    /**
     * {@link MySqlDaoFactory} instance. Singleton pattern.
     */
    private static final MySqlDaoFactory FACTORY = new MySqlDaoFactory();

    /**
     * @return {@link MySqlDaoFactory} instance
     */
    public static MySqlDaoFactory getInstance() {
        return FACTORY;
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
