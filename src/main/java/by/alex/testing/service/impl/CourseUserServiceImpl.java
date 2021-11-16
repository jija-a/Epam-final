package by.alex.testing.service.impl;

import by.alex.testing.dao.*;
import by.alex.testing.dao.mysql.CourseUserDaoImpl;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.*;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;

import java.sql.Connection;
import java.util.List;

public class CourseUserServiceImpl implements CourseUserService {

    private static final CourseUserService instance = new CourseUserServiceImpl();

    private CourseDao courseDao;
    private UserDao userDao;
    private CourseCategoryDao courseCategoryDao;
    private CourseUserDao courseUserDao;

    private CourseUserServiceImpl() {
    }

    public static CourseUserService getInstance() {
        return instance;
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
    public void addUserOnCourse(long userId, long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        courseUserDao = new CourseUserDaoImpl(connection);
        try {
            CourseUser courseUser = new CourseUser(
                    Course.builder().id(courseId).build(),
                    User.builder().id(userId).build(),
                    UserCourseStatus.REQUESTED
            );
            courseUserDao.addUserToCourse(courseUser);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void removeUserFromCourse(long userId, long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        courseUserDao = new CourseUserDaoImpl(connection);
        try {
            CourseUser courseUser = new CourseUser(
                    Course.builder().id(courseId).build(),
                    User.builder().id(userId).build(),
                    UserCourseStatus.ON_COURSE
            );
            courseUserDao.removeUserFromCourse(courseUser);
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
}
