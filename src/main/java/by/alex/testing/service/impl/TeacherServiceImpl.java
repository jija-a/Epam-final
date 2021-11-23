package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.CourseCategoryDaoImpl;
import by.alex.testing.dao.mysql.CourseDaoImpl;
import by.alex.testing.dao.mysql.CourseUserDaoImpl;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.domain.*;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TeacherService;
import by.alex.testing.service.validator.CourseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger =
            LoggerFactory.getLogger(TeacherServiceImpl.class);

    private static final TeacherService instance = new TeacherServiceImpl();

    public static TeacherService getInstance() {
        return instance;
    }

    private final TransactionHandler handler;
    private final CourseDaoImpl courseDao;
    private final CourseUserDaoImpl courseUserDao;
    private final UserDaoImpl userDao;
    private final CourseCategoryDaoImpl courseCategoryDao;

    private TeacherServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseDao = factory.getCourseDao();
        this.courseUserDao = factory.getCourseUserDao();
        this.userDao = factory.getUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Course> readAllCourses(int start, int recOnPage, long userId) throws ServiceException {

        try {
            handler.begin(courseDao, userDao, courseCategoryDao);
            List<Course> courses = courseDao.readByOwnerId(userId, start, recOnPage);
            for (Course course : courses) {
                this.setCourseLinks(course);
            }

            handler.commit();
            return courses;
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
    public void deleteUserFromCourse(CourseUser courseUser)
            throws ServiceException {

        try {
            handler.begin(courseUserDao);
            courseUserDao.removeUserFromCourse(courseUser);
            handler.commit();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public List<String> createCourse(Course course) throws ServiceException {
        List<String> errors = CourseValidator.validate(course);
        if (errors.isEmpty()) {
            try {
                handler.begin(courseDao, courseUserDao);

                courseDao.create(course);
                handler.commit();

                CourseUser courseUser = new CourseUser(
                        Course.builder().id(course.getId()).build(),
                        User.builder().id(course.getOwner().getId()).build(),
                        UserCourseStatus.ON_COURSE
                );
                courseUserDao.create(courseUser);

                handler.commit();
            } catch (DaoException e) {
                handler.rollback();
                throw new ServiceException(e.getMessage(), e);
            } finally {
                handler.end();
            }
        }
        return errors;
    }

    @Override
    public List<String> updateCourse(Course course) throws ServiceException {
        List<String> errors = CourseValidator.validate(course);
        if (errors.isEmpty()) {
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
        return errors;
    }

    @Override
    public Integer countAllCourses(long userId) throws ServiceException {

        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.countOwnerCourses(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

}
