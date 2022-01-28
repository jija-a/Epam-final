package by.alex.testing.service.impl;

import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CourseUserServiceImpl implements CourseUserService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CourseUserServiceImpl.class);

    /**
     * {@link CourseUserServiceImpl} instance. Singleton pattern.
     */
    private static final CourseUserService SERVICE =
            new CourseUserServiceImpl();

    /**
     * @return {@link CourseUserServiceImpl} instance
     */
    public static CourseUserService getInstance() {
        return SERVICE;
    }

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    /**
     * @see CourseUserDao
     */
    private final CourseUserDao courseUserDao;

    /**
     * @see CourseDao
     */
    private final CourseDao courseDao;

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    private CourseUserServiceImpl() {
        DaoFactory daoFactory =
                DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = daoFactory.getUserDao();
        this.courseUserDao = daoFactory.getCourseUserDao();
        this.courseDao = daoFactory.getCourseDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<User> findCourseUsers(final long courseId)
            throws ServiceException {
        LOGGER.info("Searching for users on courseId: {}", courseId);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.findByCourseId(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseUser> findCourseUsersByName(final int start,
                                                  final int recordsPerPage,
                                                  final long id,
                                                  final String search,
                                                  final User teacher)
            throws ServiceException {
        LOGGER.info("Searching for users on course: {}, req: {}",
                id, search);
        try {
            handler.beginNoTransaction(userDao, courseDao);
            List<CourseUser> courseUsers = new ArrayList<>();
            List<User> users =
                    userDao.findByCourseId(start, recordsPerPage, id, search);
            for (User user : users) {
                CourseUser courseUser =
                        courseUserDao.findByUserAndCourseId(user.getId(), id);
                courseUser.setUser(user);
                courseUsers.add(courseUser);
            }
            return courseUsers;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public CourseUser findCourseUser(final long courseId, final long userId)
            throws ServiceException {
        LOGGER.info("Searching for course user, course id: {}, user id: {}",
                courseId, userId);
        try {
            handler.beginNoTransaction(courseUserDao, userDao, courseDao);
            CourseUser courseUser =
                    courseUserDao.findByUserAndCourseId(userId, courseId);
            if (courseUser != null) {
                Course course =
                        courseDao.findOne(courseUser.getCourse().getId());
                courseUser.setCourse(course);
                User user = userDao.findOne(courseUser.getUser().getId());
                courseUser.setUser(user);
            }
            return courseUser;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseUsers(final long courseId)
            throws ServiceException {
        LOGGER.info("Counting all course users, course id: {}", courseId);
        try {
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.count(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseUsers(final long courseId, final String search)
            throws ServiceException {
        LOGGER.info("Counting all users, course id: {}, req: {}",
                courseId, search);
        try {
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.count(courseId, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }
}
