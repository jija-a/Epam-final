package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.CourseCategoryDaoImpl;
import by.alex.testing.dao.mysql.CourseDaoImpl;
import by.alex.testing.dao.mysql.CourseUserDaoImpl;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseUserService;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CourseUserServiceImpl implements CourseUserService {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseUserServiceImpl.class);

    private static final CourseUserService instance = new CourseUserServiceImpl();

    public static CourseUserService getInstance() {
        return instance;
    }

    private final UserDaoImpl userDao;
    private final CourseUserDaoImpl courseUserDao;
    private final CourseCategoryDaoImpl courseCategoryDao;
    private final CourseDaoImpl courseDao;
    private final TransactionHandler handler;

    private CourseUserServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseUserDao = factory.getCourseUserDao();
        this.userDao = factory.getUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.courseDao = factory.getCourseDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<User> findCourseUsersByName(int start, int recOnPage, long courseId, String search)
            throws ServiceException {

        logger.info("Searching for users on courseId - {}, by search request - {}", courseId, search);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByCourseId(start, recOnPage, courseId, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<User> findCourseUsers(int start, int recOnPage, long courseId)
            throws ServiceException {

        logger.info("Searching for users on courseId - {}", courseId);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByCourseId(start, recOnPage, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseUsers(long courseId, String search) throws ServiceException {

        try {
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.count(courseId, search);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void update(CourseUser courseUser) throws ServiceException {

        try {
            handler.begin(courseUserDao);
            courseUserDao.updateCourseUser(courseUser);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    @Override
    public void create(CourseUser courseUser) throws ServiceException {

        try {
            handler.begin(courseUserDao);
            courseUserDao.create(courseUser);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    @Override
    public List<CourseUser> findRequestsOnCourse(long userId) throws ServiceException {

        try {
            handler.begin(courseUserDao, userDao, courseCategoryDao, courseDao);

            List<CourseUser> courseUsers = courseUserDao.readAllRequests(userId);
            for (CourseUser courseUser : courseUsers) {
                Course course = courseDao.readById(courseUser.getCourse().getId());
                courseUser.setCourse(course);
                this.setCourseLinks(courseUser.getCourse());
                User user = userDao.readById(courseUser.getUser().getId());
                courseUser.setUser(user);
            }

            handler.commit();
            return courseUsers;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    @Override
    public CourseUser findCourseUser(long courseId, long userId) throws ServiceException {

        try {
            handler.begin(courseUserDao, userDao, courseCategoryDao, courseDao);

            CourseUser courseUser = courseUserDao.readById(courseId, userId);
            Course course = courseDao.readById(courseUser.getCourse().getId());
            courseUser.setCourse(course);
            this.setCourseLinks(courseUser.getCourse());
            User user = userDao.readById(courseUser.getUser().getId());
            courseUser.setUser(user);

            handler.commit();
            return courseUser;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    @Override
    public void delete(CourseUser courseUser) throws ServiceException {

        try {
            handler.begin(courseUserDao);
            courseUserDao.removeUserFromCourse(courseUser);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    @Override
    public Integer countAllCourseUsers(long courseId) throws ServiceException {

        try {
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.count(courseId);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    private void setCourseLinks(Course course) throws DaoException {
        User user = userDao.readById(course.getOwner().getId());
        CourseCategory category = courseCategoryDao.readById(course.getCategory().getId());
        course.setOwner(user);
        course.setCategory(category);
    }
}
