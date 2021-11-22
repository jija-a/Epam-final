package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.*;
import by.alex.testing.domain.*;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    private final TestDaoImpl testDao;
    private final TransactionHandler handler;

    private CourseServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseUserDao = factory.getCourseUserDao();
        this.userDao = factory.getUserDao();
        this.courseDao = factory.getCourseDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.testDao = factory.getTestDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Course> readCourseByTitle(int start, int recOnPage, String title) throws ServiceException {
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
    public List<Quiz> readAllTestsByCourseName(long courseId) throws ServiceException {
        List<Quiz> quizzes;
        try {
            handler.beginNoTransaction(testDao);
            quizzes = testDao.readAllTestsByCourseId(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
        return quizzes;
    }

    @Override
    public void removeTestFromCourse(long testId) throws ServiceException {
        try {
            handler.begin(testDao);
            testDao.delete(testId);
            handler.commit();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public Course readCourseById(long courseId) throws ServiceException {
        try {
            handler.begin(courseDao, userDao, courseCategoryDao);
            Course course = courseDao.readById(courseId);
            this.setCourseLinks(course);
            return course;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
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

    @Override
    public void updateCourseInfo(Course course) throws ServiceException {
        try {
            handler.begin(courseDao);
            courseDao.update(course);
            handler.commit();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public List<CourseCategory> readAllCourseCategories(int start, int recOnPage) throws ServiceException {
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.readAll(start, recOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Course> readUserCourses(Long userId) throws ServiceException {
        try {
            handler.begin(courseUserDao, courseDao, courseCategoryDao, userDao);
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
            handler.end();
        }
    }

    @Override
    public List<Course> readTeacherCourses(Long userId) throws ServiceException {
        try {
            handler.begin(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<Course> courses = courseDao.readByOwnerId(userId);
            for (Course course : courses) {
                this.setCourseLinks(course);
                courses.add(course);
            }
            return courses;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
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

    @Override
    public List<CourseCategory> readCourseCategoriesByTitle(int start, int recOnPage, String search)
            throws ServiceException {

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.readByTitle(start, recOnPage, search);
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseCategories(String search) throws ServiceException {

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.count(search);
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseCategories() throws ServiceException {

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.count();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void deleteCourseCategory(long id) throws ServiceException {
        try {
            handler.begin(courseCategoryDao);
            courseCategoryDao.delete(id);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }

}
