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
import by.alex.testing.domain.User;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseServiceImpl.class);

    private static final CourseService instance = new CourseServiceImpl();

    public static CourseService getInstance() {
        return instance;
    }

    private final CourseDaoImpl courseDao;
    private final UserDaoImpl userDao;
    private final CourseCategoryDaoImpl courseCategoryDao;
    private final CourseUserDaoImpl courseUserDao;
    private final TransactionHandler handler;

    private CourseServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseUserDao = factory.getCourseUserDao();
        this.userDao = factory.getUserDao();
        this.courseDao = factory.getCourseDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Course> readCourseByTitle(int start, int recOnPage, String title) throws ServiceException {
        logger.debug("Reading course by title, start - {}, rerOnPage - {}, title - {}", start, recOnPage, title);
        try {
            handler.beginNoTransaction(courseDao);
            List<Course> courses = courseDao.readCourseByTitle(title, start, recOnPage);
            for (Course course : courses) {
                this.setCourseLinks(course);
            }
            return courses;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourses(String search) throws ServiceException {
        logger.info("Counting courses");
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.count(search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourses() throws ServiceException {
        logger.info("Counting courses");
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Course> readAllCourses(int start, int recOnPage) throws ServiceException {
        try {
            handler.begin(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<Course> courses = courseDao.readAll(start, recOnPage);
            for (Course course : courses) {
                this.setCourseLinks(course);
            }
            return courses;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    @Override
    public Course readCourseById(long courseId) throws ServiceException {
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.readById(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void deleteCourse(long courseId) throws ServiceException {
        try {
            handler.begin(courseDao);
            courseDao.delete(courseId);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

    private void setCourseLinks(Course course) throws DaoException {
        User user = userDao.readById(course.getOwner().getId());
        CourseCategory category = courseCategoryDao.readById(course.getCategory().getId());
        course.setOwner(user);
        course.setCategory(category);
    }
}
