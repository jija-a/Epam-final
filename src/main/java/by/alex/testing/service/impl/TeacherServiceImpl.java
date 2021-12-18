package by.alex.testing.service.impl;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.dao.AttendanceDao;
import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.LessonDao;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.AttendanceCalculator;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TeacherService;
import by.alex.testing.service.validator.AttendanceValidator;
import by.alex.testing.service.validator.CourseValidator;
import by.alex.testing.service.validator.LessonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class TeacherServiceImpl implements TeacherService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TeacherServiceImpl.class);

    /**
     * Error message.
     */
    private static final String ACCESS_ERROR =
            "Teacher id: %s tries to manipulate not his own course data";

    /**
     * {@link TeacherService} instance. Singleton pattern.
     */
    private static final TeacherService SERVICE = new TeacherServiceImpl();

    /**
     * @return {@link TeacherService} instance
     */
    public static TeacherService getInstance() {
        return SERVICE;
    }

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    /**
     * @see CourseDao
     */
    private final CourseDao courseDao;

    /**
     * @see CourseUserDao
     */
    private final CourseUserDao courseUserDao;

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    /**
     * @see LessonDao
     */
    private final LessonDao lessonDao;

    /**
     * @see AttendanceDao
     */
    private final AttendanceDao attendanceDao;

    private TeacherServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseDao = factory.getCourseDao();
        this.courseUserDao = factory.getCourseUserDao();
        this.userDao = factory.getUserDao();
        this.lessonDao = factory.getLessonDao();
        this.attendanceDao = factory.getAttendanceDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<String> updateCourse(final Course course, final User teacher)
            throws ServiceException, AccessException {

        List<String> errors = CourseValidator.validate(course);
        if (errors.isEmpty()) {
            try {
                checkIfAvailableCourseData(course.getId(), teacher);
                handler.begin(courseDao);
                long id = teacher.getId();
                String name = course.getName();
                Course existing = courseDao.findByOwnerIdAndTitle(id, name);
                if (existing != null
                        && !existing.getId().equals(course.getId())) {
                    errors.add(MessageManager.INSTANCE
                            .getMessage(MessageConstant.ALREADY_EXISTS));
                } else {
                    LOGGER.info("Updating course");
                    if (!courseDao.update(course)) {
                        errors.add(MessageManager.INSTANCE
                                .getMessage(MessageConstant.UPDATE_ERROR));
                    }
                }
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
    public boolean deleteCourse(final long courseId, final User teacher)
            throws ServiceException, AccessException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            boolean isDeleted;
            handler.begin(courseDao);
            LOGGER.info("Deleting course by id - {}", courseId);
            isDeleted = courseDao.delete(courseId);
            handler.commit();
            return isDeleted;
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public List<Lesson> findAllLessons(final long courseId,
                                       final int start,
                                       final int recordsPerPage,
                                       final User teacher)
            throws ServiceException, AccessException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            LOGGER.info("Reading all lessons by course id - {}", courseId);
            handler.beginNoTransaction(lessonDao, courseDao);
            return lessonDao.findByCourseId(courseId, start, recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateLesson(final Lesson lesson, final User teacher)
            throws ServiceException, AccessException {

        List<String> errors = LessonValidator.validate(lesson);
        if (errors.isEmpty()) {
            try {
                checkIfAvailableCourseData(lesson.getCourseId(), teacher);
                LOGGER.info("Updating lesson, id - {}", lesson.getId());
                handler.begin(lessonDao);
                boolean isUpdated = lessonDao.update(lesson);
                if (!isUpdated) {
                    errors.add(MessageManager.INSTANCE
                            .getMessage(MessageConstant.UPDATE_ERROR));
                }
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
    public boolean deleteLesson(final long lessonId,
                                final long courseId,
                                final User teacher)
            throws ServiceException, AccessException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            boolean isDeleted;
            LOGGER.info("Deleting lesson, lesson id - {}", lessonId);
            handler.begin(lessonDao, userDao, courseUserDao, attendanceDao);
            isDeleted = lessonDao.delete(lessonId);
            this.updateAttendances(courseId);
            handler.commit();
            return isDeleted;
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    private void updateAttendances(final long courseId) throws DaoException {
        List<User> users = userDao.findByCourseId(courseId);
        List<CourseUser> courseUsers = new ArrayList<>();
        for (User user : users) {
            CourseUser courseUser =
                    courseUserDao.findByUserAndCourseId(user.getId(), courseId);
            courseUsers.add(courseUser);
        }
        for (CourseUser courseUser : courseUsers) {
            this.calculateAttendance(courseUser);
            courseUserDao.update(courseUser);
        }
    }

    @Override
    public List<Attendance> findAllAttendances(final long lessonId,
                                               final long courseId,
                                               final User teacher)
            throws ServiceException, AccessException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            handler.beginNoTransaction(attendanceDao, userDao);
            LOGGER.info("Reading all attendances by lesson id - {}", lessonId);
            List<Attendance> attendances =
                    attendanceDao.findByLessonId(lessonId);
            for (Attendance attendance : attendances) {
                User user = userDao.findOne(attendance.getStudent().getId());
                attendance.setStudent(user);
            }
            return attendances;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateAttendance(final Attendance attendance,
                                         final long courseId,
                                         final User teacher)
            throws ServiceException, AccessException {

        List<String> errors = AttendanceValidator.validate(attendance);
        if (errors.isEmpty()) {
            try {
                checkIfAvailableCourseData(courseId, teacher);
                LOGGER.info("Updating attendance, id: {}", attendance.getId());
                handler.begin(attendanceDao, courseUserDao, attendanceDao);
                if (!attendanceDao.update(attendance)) {
                    errors.add(MessageManager.INSTANCE
                            .getMessage(MessageConstant.UPDATE_ERROR));
                }
                long studentId = attendance.getStudent().getId();
                CourseUser courseUser = courseUserDao
                        .findByUserAndCourseId(studentId, courseId);
                this.calculateAttendance(courseUser
                );
                if (!courseUserDao.update(courseUser)) {
                    errors.add(MessageManager.INSTANCE
                            .getMessage(MessageConstant.UPDATE_ERROR));
                }
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
    public List<CourseUser> findCourseUsers(final int start,
                                            final int recordsPerPage,
                                            final long courseId,
                                            final User teacher)
            throws ServiceException, AccessException {
        LOGGER.info("Searching users on course: {}, start: {}, rec: {}",
                courseId, start, recordsPerPage);
        try {
            checkIfAvailableCourseData(courseId, teacher);
            List<CourseUser> courseUsers = new ArrayList<>();
            handler.beginNoTransaction(userDao, courseUserDao);
            List<User> users =
                    userDao.findByCourseId(start, recordsPerPage, courseId);
            for (User user : users) {
                long id = user.getId();
                CourseUser courseUser =
                        courseUserDao.findByUserAndCourseId(id, courseId);
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
    public boolean updateCourseUser(final CourseUser courseUser,
                                    final User teacher)
            throws ServiceException, AccessException {
        boolean isUpdated;
        try {
            checkIfAvailableCourseData(courseUser.getCourse().getId(), teacher);
            handler.begin(courseUserDao);
            isUpdated = courseUserDao.update(courseUser);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
        return isUpdated;
    }

    @Override
    public boolean declineRequest(final CourseUser courseUser,
                                  final User teacher)
            throws ServiceException, AccessException {
        boolean isDeleted;
        long courseId = courseUser.getCourse().getId();
        long userId = courseUser.getUser().getId();
        LOGGER.info("Deleting course user, course id - {}, user id - {}",
                courseId, userId);
        try {
            checkIfAvailableCourseData(courseUser.getCourse().getId(), teacher);
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

    @Override
    public boolean deleteUserFromCourse(final CourseUser courseUser,
                                        final User teacher)
            throws ServiceException, AccessException {
        boolean isDeleted;
        long courseId = courseUser.getCourse().getId();
        long userId = courseUser.getUser().getId();
        LOGGER.info("Deleting user from course, courseId - {}, userId - {}",
                courseId, userId);
        try {
            checkIfAvailableCourseData(courseUser.getCourse().getId(), teacher);
            handler.begin(courseDao, courseUserDao, attendanceDao);
            isDeleted = courseUserDao.delete(courseUser);
            attendanceDao.delete(courseUser.getUser().getId());
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
        return isDeleted;
    }

    private void checkIfAvailableCourseData(final long courseId,
                                            final User teacher)
            throws AccessException, DaoException {
        LOGGER.info("Defining if teacher can manipulate course data "
                        + "course id - {}, teacher id - {}",
                courseId, teacher.getId());
        try {
            handler.beginNoTransaction(courseDao);
            Course course = courseDao.findOne(courseId);
            if (course == null
                    || !(course.getOwner().getId().equals(teacher.getId())
                    || teacher.getRole().equals(UserRole.ADMIN))) {
                throw new AccessException(
                        String.format(ACCESS_ERROR, teacher.getId()));
            }
        } finally {
            handler.endNoTransaction();
        }
    }

    private void calculateAttendance(final CourseUser courseUser)
            throws DaoException {
        List<Attendance> attendances =
                attendanceDao.findByCourseUser(courseUser);
        Double percent =
                AttendanceCalculator.calcAttendancePercent(attendances);
        Double rating = AttendanceCalculator.calcCourseRating(attendances);
        courseUser.setRating(rating);
        courseUser.setAttendancePercent(percent);
    }
}
