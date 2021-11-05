package by.alex.testing.service.impl;

import by.alex.testing.dao.*;
import by.alex.testing.dao.mysql.*;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.*;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;

import java.sql.Connection;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;
    private UserDao userDao;
    private CourseCategoryDao courseCategoryDao;

    public CourseServiceImpl() {
        // TODO document why this constructor is empty
    }

    @Override
    public List<Course> readCourseByTitle(String title) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDao = new UserDaoImpl(connection);
        courseDao = new CourseDaoImpl(connection);
        courseCategoryDao = new CourseCategoryDaoImpl(connection);
        try {
            List<Course> courses = courseDao.readCourseByTitle(title);
            for (Course course : courses) {
                User user = userDao.readById(course.getOwner().getId());
                CourseCategory category = courseCategoryDao.readById(course.getCategory().getId());
                course.setOwner(user);
                course.setCategory(category);
            }
            return courses;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<User> readUsersByCourseId(Long id) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDao = new UserDaoImpl(connection);
        try {
            return userDao.readByCourseId(id);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<Quiz> readAllTestsByCourseName(long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        List<Quiz> quizzes;
        try {
            quizzes = testDao.readAllTestsByCourseId(courseId);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return quizzes;
    }

    @Override
    public void removeTestFromCourse(long testId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        try {
            testDao.delete(testId);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void removeUserFromCourse(long userId, long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        CourseUserDao courseUserDao = new CourseUserDaoImpl(connection);
        try {
            CourseUser courseUser = new CourseUser(
                    Course.builder().id(courseId).build(),
                    User.builder().id(userId).build()
            );
            courseUserDao.removeUserFromCourse(courseUser);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Course readCourseById(long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDao = new UserDaoImpl(connection);
        courseDao = new CourseDaoImpl(connection);
        courseCategoryDao = new CourseCategoryDaoImpl(connection);
        try {
            Course course = courseDao.readById(courseId);
            User user = userDao.readById(course.getOwner().getId());
            CourseCategory category = courseCategoryDao.readById(course.getCategory().getId());
            course.setOwner(user);
            course.setCategory(category);
            return course;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

}
