package by.alex.testing.service.impl;

import by.alex.testing.dao.*;
import by.alex.testing.dao.mysql.*;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.*;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;
    private UserDao userDao;
    private CourseCategoryDao courseCategoryDao;
    private CourseUserDao courseUserDao;

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
                this.setCourseLinks(course);
            }
            return courses;
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
    public Course readCourseById(long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDao = new UserDaoImpl(connection);
        courseDao = new CourseDaoImpl(connection);
        courseCategoryDao = new CourseCategoryDaoImpl(connection);
        try {
            Course course = courseDao.readById(courseId);
            this.setCourseLinks(course);
            return course;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private void setCourseLinks(Course course) throws DaoException {
        User user = userDao.readById(course.getOwner().getId());
        CourseCategory category = courseCategoryDao.readById(course.getCategory().getId());
        course.setOwner(user);
        course.setCategory(category);
    }

    @Override
    public void updateCourseInfo(Course course) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        courseDao = new CourseDaoImpl(connection);
        try {
            courseDao.update(course);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<CourseCategory> readAllCourseCategories() throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        courseCategoryDao = new CourseCategoryDaoImpl(connection);
        try {
            return courseCategoryDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<Course> readUserCourses(Long userId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDao = new UserDaoImpl(connection);
        courseDao = new CourseDaoImpl(connection);
        courseCategoryDao = new CourseCategoryDaoImpl(connection);
        courseUserDao = new CourseUserDaoImpl(connection);
        try {
            List<Course> courses = new ArrayList<>();
            List<CourseUser> userCourses = courseUserDao.readAllUserCourses(userId);
            for (CourseUser courseUser : userCourses) {
                Course course = courseDao.readById(courseUser.getCourse().getId());
                this.setCourseLinks(course);
                courses.add(course);
            }
            return courses;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
