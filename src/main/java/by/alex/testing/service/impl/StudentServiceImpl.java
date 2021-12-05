package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.*;
import by.alex.testing.domain.*;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final AttendanceDaoImpl attendanceDao;
    private final LessonDaoImpl lessonDao;
    private final TransactionHandler handler;

    protected StudentServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseDao = factory.getCourseDao();
        this.courseUserDao = factory.getCourseUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.userDao = factory.getUserDao();
        this.attendanceDao = factory.getAttendanceDao();
        this.lessonDao = factory.getLessonDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<CourseUser> readStudentCourses(long studentId, int start, int recordsPerPage) throws ServiceException {

        List<CourseUser> userCourses = new ArrayList<>();
        try {
            List<Course> allCourses =
                    this.readStudentCoursesByStatus(studentId, UserCourseStatus.ON_COURSE, start, recordsPerPage);
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            for (Course course : allCourses) {
                this.setCourseLinks(course);
                CourseUser userCourse = courseUserDao.readById(course.getId(), studentId);
                userCourse.setCourse(course);
                userCourses.add(userCourse);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
        return userCourses;
    }

    @Override
    public List<Course> readAvailableCourses(long studentId, int start, int recordsPerPage)
            throws ServiceException {

        try {
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<Course> courses = courseDao.readExcludingUserCourses(studentId, start, recordsPerPage);
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
    public List<Course> readAvailableCourses(long studentId, int start, int recordsPerPage, String search) throws ServiceException {
        try {
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<Course> courses = courseDao.readExcludingUserCourses(studentId, start, recordsPerPage, search);
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
    public List<Course> readStudentCoursesByStatus(long studentId, UserCourseStatus status) throws ServiceException {
        try {
            List<Course> courses = new ArrayList<>();
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<CourseUser> courseUser = courseUserDao.readUserCoursesByStatus(studentId, status);
            for (CourseUser courseU : courseUser) {
                Course course = courseDao.readById(courseU.getCourse().getId());
                courses.add(course);
            }
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
    public List<Course> readStudentCoursesByStatus(long studentId, UserCourseStatus status, int start, int recordsPerPage) throws ServiceException {
        try {
            List<Course> courses = new ArrayList<>();
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<CourseUser> courseUser =
                    courseUserDao.readUserCoursesByStatus(studentId, status, start, recordsPerPage);
            for (CourseUser courseU : courseUser) {
                Course course = courseDao.readById(courseU.getCourse().getId());
                courses.add(course);
            }
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
    public Map<Lesson, Attendance> findAllLessons(long courseId, long studentId, int start, int recordsPerPage) throws ServiceException {

        Map<Lesson, Attendance> map = new HashMap<>();
        try {
            logger.info("Reading all lessons by course id - {}", courseId);
            handler.beginNoTransaction(lessonDao, attendanceDao);
            List<Lesson> lessons = lessonDao.readByCourseAndStudentId(courseId, studentId, start, recordsPerPage);
            for (Lesson lesson : lessons) {
                Attendance attendance = attendanceDao.readByLessonAndStudentId(lesson.getId(), studentId);
                map.put(lesson, attendance);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
        return map;
    }

    @Override
    public int countStudentLessons(long courseId, long studentId) throws ServiceException {
        try {
            logger.info("Counting all lessons");
            handler.beginNoTransaction(lessonDao);
            return lessonDao.count(courseId, studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public int countStudentCourses(long studentId) throws ServiceException {
        try {
            logger.info("Counting student courses");
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.countStudentCourses(studentId, UserCourseStatus.ON_COURSE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public int countAvailableCourses(long studentId) throws ServiceException {
        try {
            logger.info("Counting student courses");
            handler.beginNoTransaction(courseUserDao);
            return courseDao.countAvailableCourses(studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public int countAvailableCourses(long studentId, String search) throws ServiceException {
        try {
            logger.info("Counting student courses");
            handler.beginNoTransaction(courseUserDao);
            return courseDao.countAvailableCourses(studentId, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public boolean signOnCourse(CourseUser courseUser) throws ServiceException {
        boolean isSigned;
        try {
            logger.info("Counting student courses");
            handler.begin(courseUserDao);
            isSigned = courseUserDao.create(courseUser);
            handler.commit();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
        return isSigned;
    }

    @Override
    public boolean leaveCourse(CourseUser courseUser) throws ServiceException {
        try {
            boolean isDeleted;
            handler.begin(courseUserDao);
            logger.info("Deleting course user, course id - {}, user id - {}",
                    courseUser.getCourse().getId(), courseUser.getUser().getId());
            isDeleted = courseUserDao.delete(courseUser);
            handler.commit();
            return isDeleted;
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
