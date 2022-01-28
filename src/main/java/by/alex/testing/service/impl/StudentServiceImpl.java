package by.alex.testing.service.impl;

import by.alex.testing.dao.AttendanceDao;
import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.LessonDao;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServiceImpl implements StudentService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(StudentServiceImpl.class);

    /**
     * {@link StudentService} instance. Singleton pattern.
     */
    private static final StudentService SERVICE = new StudentServiceImpl();

    /**
     * @return {@link StudentService} instance
     */
    public static StudentService getInstance() {
        return SERVICE;
    }

    /**
     * @see CourseDao
     */
    private final CourseDao courseDao;

    /**
     * @see CourseUserDao
     */
    private final CourseUserDao courseUserDao;

    /**
     * @see CourseCategoryDao
     */
    private final CourseCategoryDao courseCategoryDao;

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    /**
     * @see AttendanceDao
     */
    private final AttendanceDao attendanceDao;

    /**
     * @see LessonDao
     */
    private final LessonDao lessonDao;

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    private StudentServiceImpl() {
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
    public List<CourseUser> readStudentCourses(final long id,
                                               final int start,
                                               final int recordsPerPage)
            throws ServiceException {

        List<CourseUser> userCourses = new ArrayList<>();
        try {
            List<Course> allCourses = this.readStudentCourses(
                    id, UserCourseStatus.ON_COURSE, start, recordsPerPage);
            handler.beginNoTransaction(courseUserDao, courseDao,
                    courseCategoryDao, userDao);
            for (Course course : allCourses) {
                this.setCourseLinks(course);
                CourseUser userCourse =
                        courseUserDao.findByUserAndCourseId(id, course.getId());
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
    public List<Course> readAvailableCourses(final long id,
                                             final int start,
                                             final int recordsPerPage)
            throws ServiceException {

        try {
            handler.beginNoTransaction(courseUserDao, courseDao,
                    courseCategoryDao, userDao);
            List<Course> courses =
                    courseDao.findNotUserCourses(id, start, recordsPerPage);
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
    public List<Course> readAvailableCourses(final long studentId,
                                             final int start,
                                             final int recordsPerPage,
                                             final String search)
            throws ServiceException {
        try {
            handler.beginNoTransaction(courseUserDao, courseDao,
                    courseCategoryDao, userDao);
            List<Course> courses = courseDao.findNotUserCourses(
                    studentId, start, recordsPerPage, search);
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
    public List<Course> readStudentCourses(final long studentId,
                                           final UserCourseStatus status)
            throws ServiceException {
        try {
            List<Course> courses = new ArrayList<>();
            handler.beginNoTransaction(courseUserDao, courseDao,
                    courseCategoryDao, userDao);
            List<CourseUser> courseUser =
                    courseUserDao.findByUserIdAndStatus(studentId, status);
            for (CourseUser courseU : courseUser) {
                Course course = courseDao.findOne(courseU.getCourse().getId());
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
    public List<Course> readStudentCourses(final long studentId,
                                           final UserCourseStatus status,
                                           final int start,
                                           final int recordsPerPage)
            throws ServiceException {
        try {
            List<Course> courses = new ArrayList<>();
            handler.beginNoTransaction(courseUserDao, courseDao,
                    courseCategoryDao, userDao);
            List<CourseUser> courseUser = courseUserDao.findByUserIdAndStatus(
                    studentId, status, start, recordsPerPage);
            for (CourseUser courseU : courseUser) {
                Course course = courseDao.findOne(courseU.getCourse().getId());
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
    public Map<Lesson, Attendance> findLessons(final long courseId,
                                               final long studentId,
                                               final int start,
                                               final int recordsPerPage)
            throws ServiceException {
        LOGGER.info("Searching all lessons by course id - {}", courseId);
        Map<Lesson, Attendance> map = new HashMap<>();
        try {
            handler.beginNoTransaction(lessonDao, attendanceDao);
            List<Lesson> lessons = lessonDao.findByCourseAndStudentId(
                    courseId, studentId, start, recordsPerPage);
            for (Lesson lesson : lessons) {
                long id = lesson.getId();
                Attendance attendance =
                        attendanceDao.findByLessonAndStudentId(id, studentId);
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
    public Integer countAvailableCourses(final long studentId)
            throws ServiceException {
        LOGGER.info("Counting available courses for student, id: {}",
                studentId);
        try {
            handler.beginNoTransaction(courseUserDao);
            return courseDao.countAvailableCourses(studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAvailableCourses(final long id, final String search)
            throws ServiceException {
        try {
            LOGGER.info("Counting available courses, req: {}", search);
            handler.beginNoTransaction(courseUserDao);
            return courseDao.countAvailableCourses(id, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public boolean signOnCourse(final CourseUser courseUser)
            throws ServiceException {
        boolean isSigned;
        long userId = courseUser.getUser().getId();
        long courseId = courseUser.getCourse().getId();
        LOGGER.info("Signing student: {}, on course: {}", userId, courseId);
        try {
            handler.begin(courseUserDao);
            isSigned = courseUserDao.save(courseUser);
            handler.commit();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
        return isSigned;
    }

    @Override
    public boolean leaveCourse(final CourseUser courseUser)
            throws ServiceException {
        boolean isDeleted;
        long userId = courseUser.getUser().getId();
        long courseId = courseUser.getCourse().getId();
        LOGGER.info("Deleting student: {}, on course: {}",
                userId, courseId);
        try {
            handler.begin(courseUserDao);
            isDeleted = courseUserDao.delete(courseUser);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
        return isDeleted;
    }

    private void setCourseLinks(final Course course) throws DaoException {
        User user = userDao.findOne(course.getOwner().getId());
        CourseCategory category =
                courseCategoryDao.findOne(course.getCategory().getId());
        course.setOwner(user);
        course.setCategory(category);
    }
}
