package by.alex.testing.dao;

import by.alex.testing.dao.mysql.*;

public abstract class DaoFactory {
    public enum DaoType{
        MYSQL;
    }

    public abstract AnswerDaoImpl getAnswerDao();
    public abstract CourseCategoryDaoImpl getCourseCategoryDao();
    public abstract CourseDaoImpl getCourseDao();
    public abstract CourseUserDaoImpl getCourseUserDao();
    public abstract QuestionDaoImpl getQuestionDao();
    public abstract TestDaoImpl getTestDao();
    public abstract TestResultDaoImpl getTestResultDao();
    public abstract UserDaoImpl getUserDao();

    public static DaoFactory getDaoFactory(DaoType type) throws RuntimeException {
        switch (type) {
            case MYSQL:
                return MySqlDaoFactory.getInstance();
            default:
                throw new RuntimeException("Wrong DAO type");
        }
    }

}
