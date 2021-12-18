package by.alex.testing.service.impl;

import by.alex.testing.dao.AttendanceDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.LessonDao;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.service.AttendanceCalculator;
import by.alex.testing.service.LessonService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.validator.LessonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class LessonServiceImpl implements LessonService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(LessonServiceImpl.class);

    /**
     * {@link LessonService} instance. Singleton pattern.
     */
    private static final LessonService SERVICE = new LessonServiceImpl();

    /**
     * @return {@link LessonService} instance
     */
    public static LessonService getInstance() {
        return SERVICE;
    }

    /**
     * @see LessonDao
     */
    private final LessonDao lessonDao;

    /**
     * @see AttendanceDao
     */
    private final AttendanceDao attendanceDao;

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    /**
     * @see CourseUserDao
     */
    private final CourseUserDao courseUserDao;

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    private LessonServiceImpl() {
        DaoFactory daoFactory =
                DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.lessonDao = daoFactory.getLessonDao();
        this.attendanceDao = daoFactory.getAttendanceDao();
        this.userDao = daoFactory.getUserDao();
        this.courseUserDao = daoFactory.getCourseUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public Lesson findLessonById(final long id) throws ServiceException {
        LOGGER.info("Reading lesson by id: {}", id);

        try {
            handler.beginNoTransaction(lessonDao);
            return lessonDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> createLesson(final Lesson lesson)
            throws ServiceException {
        LOGGER.info("Creating lesson");

        List<String> errors = LessonValidator.validate(lesson);
        if (errors.isEmpty()) {
            try {
                handler.begin(lessonDao, attendanceDao, userDao, courseUserDao);
                lessonDao.save(lesson);
                for (Attendance attendance : lesson.getAttendances()) {
                    attendance.setLessonId(lesson.getId());
                    attendanceDao.save(attendance);
                }
                this.createAttendances(lesson.getCourseId());
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
    public int countAllLessons(final long courseId) throws ServiceException {
        LOGGER.info("Counting all lessons");

        try {
            handler.beginNoTransaction(lessonDao);
            return lessonDao.count(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public int countStudentLessons(final long courseId, final long studentId)
            throws ServiceException {
        LOGGER.info("Counting all lessons");

        try {
            handler.beginNoTransaction(lessonDao);
            return lessonDao.count(courseId, studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    private void createAttendances(final long courseId) throws DaoException {
        List<User> users = userDao.findByCourseId(courseId);
        List<CourseUser> courseUsers = new ArrayList<>();
        for (User user : users) {
            CourseUser courseUser =
                    courseUserDao.findByUserAndCourseId(user.getId(), courseId);
            courseUsers.add(courseUser);
        }
        for (CourseUser courseUser : courseUsers) {
            this.calculateAttendancePercent(courseUser);
            courseUserDao.update(courseUser);
        }
    }

    private void calculateAttendancePercent(final CourseUser courseUser)
            throws DaoException {
        LOGGER.info("Calculating user attendance");

        List<Attendance> attendances =
                attendanceDao.findByCourseUser(courseUser);
        Double percent =
                AttendanceCalculator.calcAttendancePercent(attendances);
        courseUser.setAttendancePercent(percent);
    }
}
