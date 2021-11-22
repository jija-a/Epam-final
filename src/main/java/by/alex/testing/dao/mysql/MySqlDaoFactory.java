package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoFactory;

public class MySqlDaoFactory extends DaoFactory {

    private static final MySqlDaoFactory instance = new MySqlDaoFactory();

    public static MySqlDaoFactory getInstance(){
        return instance;
    }

    @Override
    public AnswerDaoImpl getAnswerDao() {
        return new AnswerDaoImpl();
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
    public QuestionDaoImpl getQuestionDao() {
        return new QuestionDaoImpl();
    }

    @Override
    public TestDaoImpl getTestDao() {
        return new TestDaoImpl();
    }

    @Override
    public TestResultDaoImpl getTestResultDao() {
        return new TestResultDaoImpl();
    }

    @Override
    public UserDaoImpl getUserDao() {
        return new UserDaoImpl();
    }
}
