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
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentServiceImpl.class);

    private static final StudentServiceImpl instance = new StudentServiceImpl();

    public static StudentServiceImpl getInstance() {
        return instance;
    }

    private final CourseDaoImpl courseDao;
    private final CourseUserDaoImpl courseUserDao;
    private final CourseCategoryDaoImpl courseCategoryDao;
    private final UserDaoImpl userDao;
    private final TransactionHandler handler;

    protected StudentServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseDao = factory.getCourseDao();
        this.courseUserDao = factory.getCourseUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.userDao = factory.getUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Course> readStudentCourses(long studentId) throws ServiceException {
        try {
            List<Course> courses = new ArrayList<>();
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<CourseUser> courseUser = courseUserDao.readAllUserCourses(studentId);
            for (CourseUser courseU : courseUser) {
                Course course = courseDao.readById(courseU.getCourse().getId());
                courses.add(course);
            }
            for (Course course : courses) {
                this.setCourseLinks(course);
            }
            return courses;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Course> readAvailableCourses(long studentId) throws ServiceException {
        return null;
    }

    @Override
    public void signOnCourse(CourseUser courseUser) throws ServiceException {

        try {
            handler.begin(courseUserDao);
            courseUserDao.create(courseUser);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
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
}
