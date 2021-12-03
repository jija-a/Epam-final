package by.alex.testing.service.impl;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.*;
import by.alex.testing.domain.*;
import by.alex.testing.service.CourseAccessDeniedException;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TeacherService;
import by.alex.testing.service.validator.AttendanceValidator;
import by.alex.testing.service.validator.CourseValidator;
import by.alex.testing.service.validator.LessonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger =
            LoggerFactory.getLogger(TeacherServiceImpl.class);

    private static final String ACCESS_ERROR =
            "Teacher id: %s tries to manipulate not his own course data";

    private static final TeacherService instance = new TeacherServiceImpl();

    public static TeacherService getInstance() {
        return instance;
    }

    private final TransactionHandler handler;
    private final CourseDaoImpl courseDao;
    private final CourseUserDaoImpl courseUserDao;
    private final UserDaoImpl userDao;
    private final CourseCategoryDaoImpl courseCategoryDao;
    private final LessonDaoImpl lessonDao;
    private final AttendanceDaoImpl attendanceDao;

    private TeacherServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseDao = factory.getCourseDao();
        this.courseUserDao = factory.getCourseUserDao();
        this.userDao = factory.getUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.lessonDao = factory.getLessonDao();
        this.attendanceDao = factory.getAttendanceDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Course> findAllCoursesByTeacherId(int start, int recOnPage, long teacherId)
            throws ServiceException {

        logger.info("Reading all teacher courses, start - {}, rec on page - {}, teacher id - {}",
                start, recOnPage, teacherId);
        try {
            handler.beginNoTransaction(courseDao, userDao, courseCategoryDao);
            List<Course> courses = courseDao.readByOwnerId(teacherId, start, recOnPage);
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
    public List<String> createCourse(Course course) throws ServiceException {

        logger.info("Creating course");
        List<String> errors = CourseValidator.validate(course);
        if (errors.isEmpty()) {
            try {
                handler.begin(courseDao, courseCategoryDao);
                if (isCourseExist(course.getName(), course.getOwner().getId())) {
                    errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.ALREADY_EXISTS));
                } else {
                    long categoryId = course.getCategory().getId();
                    if (courseCategoryDao.readById(categoryId) == null) {
                        throw new UnknownEntityException("Unknown category id: " + categoryId);
                    }
                    if (!courseDao.create(course)) {
                        errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.CREATE_ERROR));
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
    public List<String> updateCourse(Course course, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        List<String> errors = CourseValidator.validate(course);
        if (errors.isEmpty()) {
            try {
                checkIfAvailableCourseData(course.getId(), teacher);
                handler.begin(courseDao);
                Course existing = courseDao.readByOwnerIdAndName(teacher.getId(), course.getName());
                if (existing != null && !existing.getId().equals(course.getId())) {
                    errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.ALREADY_EXISTS));
                } else {
                    logger.info("Updating course");
                    if (!courseDao.update(course)) {
                        errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.UPDATE_ERROR));
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
    public boolean deleteCourse(long courseId, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            boolean isDeleted;
            handler.begin(courseDao);
            logger.info("Deleting course by id - {}", courseId);
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
    public Integer countAllCoursesByTeacherId(long teacherId) throws ServiceException {

        logger.info("Counting all courses by teacher id - {}", teacherId);
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.countOwnerCourses(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Lesson> findAllLessons(long courseId, int start, int recordsPerPage, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            logger.info("Reading all lessons by course id - {}", courseId);
            handler.beginNoTransaction(lessonDao, courseDao);
            return lessonDao.readByCourseId(courseId, start, recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Lesson findLessonById(long lessonId) throws ServiceException {
        try {
            logger.info("Reading lesson by id - {}", lessonId);
            handler.beginNoTransaction(lessonDao);
            return lessonDao.readById(lessonId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> createLesson(Lesson lesson) throws ServiceException {

        logger.info("Creating lesson");
        List<String> errors = LessonValidator.validate(lesson);
        if (errors.isEmpty()) {
            try {
                handler.begin(lessonDao, attendanceDao);
                lessonDao.create(lesson);
                for (Attendance attendance : lesson.getAttendances()) {
                    attendance.setLessonId(lesson.getId());
                    attendanceDao.create(attendance);
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
    public List<String> updateLesson(Lesson lesson, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        List<String> errors = LessonValidator.validate(lesson);
        if (errors.isEmpty()) {
            try {
                checkIfAvailableCourseData(lesson.getCourseId(), teacher);
                logger.info("Updating lesson, id - {}", lesson.getId());
                handler.begin(lessonDao);
                boolean isUpdated = lessonDao.update(lesson);
                if (!isUpdated) {
                    errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.UPDATE_ERROR));
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
    public boolean deleteLesson(long lessonId, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            handler.beginNoTransaction(lessonDao);
            Lesson lesson = lessonDao.readById(lessonId);
            handler.endNoTransaction();
            checkIfAvailableCourseData(lesson.getCourseId(), teacher);
            boolean isDeleted;
            logger.info("Deleting lesson, lesson id - {}", lessonId);
            handler.begin(lessonDao);
            isDeleted = lessonDao.delete(lessonId);
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
    public int countAllLessons(long courseId) throws ServiceException {
        try {
            logger.info("Counting all lessons");
            handler.beginNoTransaction(lessonDao);
            return lessonDao.count(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Attendance> findAllAttendances(long lessonId, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            handler.beginNoTransaction(lessonDao);
            Lesson lesson = lessonDao.readById(lessonId);
            handler.endNoTransaction();
            checkIfAvailableCourseData(lesson.getCourseId(), teacher);
            handler.beginNoTransaction(attendanceDao, userDao);
            logger.info("Reading all attendances by lesson id - {}", lessonId);
            List<Attendance> attendances = attendanceDao.readByLessonId(lessonId);
            for (Attendance attendance : attendances) {
                User user = userDao.readById(attendance.getStudent().getId());
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
    public Attendance findAttendance(long attendanceId) throws ServiceException {

        logger.info("Reading attendance by id - {}", attendanceId);
        try {
            handler.beginNoTransaction(attendanceDao, userDao);
            Attendance attendance = attendanceDao.readById(attendanceId);
            User user = userDao.readById(attendance.getStudent().getId());
            attendance.setStudent(user);
            return attendance;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateAttendance(Attendance attendance, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        List<String> errors = AttendanceValidator.validate(attendance);
        if (errors.isEmpty()) {
            try {
                handler.beginNoTransaction(lessonDao);
                Lesson lesson = lessonDao.readById(attendance.getLessonId());
                handler.endNoTransaction();
                checkIfAvailableCourseData(lesson.getCourseId(), teacher);
                logger.info("Updating attendance, attendance id - {}", attendance.getId());
                handler.begin(attendanceDao);
                if (!attendanceDao.update(attendance)) {
                    errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.UPDATE_ERROR));
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
    public List<CourseUser> findRequestsOnCourse(int start, int recOnPage, long teacherId)
            throws ServiceException {

        logger.info("Searching for requests on teacher courses, teacher id - {}",
                teacherId);
        try {
            handler.beginNoTransaction(courseUserDao, userDao, courseCategoryDao, courseDao);
            List<CourseUser> courseUsers = courseUserDao.readAllRequests(start, recOnPage, teacherId);
            for (CourseUser courseUser : courseUsers) {
                Course course = courseDao.readById(courseUser.getCourse().getId());
                courseUser.setCourse(course);
                User user = userDao.readById(courseUser.getUser().getId());
                courseUser.setUser(user);
            }
            return courseUsers;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public int countAllRequests(long teacherId) throws ServiceException {
        logger.info("Counting teacher courses requests, teacher id - {}",
                teacherId);
        try {
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.countRequests(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<User> findCourseUsers(long courseId) throws ServiceException {
        logger.info("Searching for users on courseId - {}", courseId);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByCourseId(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseUser> findCourseUsers(int start, int recOnPage, long courseId, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            checkIfAvailableCourseData(courseId, teacher);
            logger.info("Searching for users on courseId - {}, start - {}, rec on page - {}",
                    courseId, start, recOnPage);
            List<CourseUser> courseUsers = new ArrayList<>();
            handler.beginNoTransaction(userDao);
            List<User> users = userDao.readByCourseId(start, recOnPage, courseId);
            for (User user : users) {
                CourseUser courseUser = courseUserDao.readById(courseId, user.getId());
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
    public List<CourseUser> findCourseUsersByName(int start, int recOnPage,
                                                  long courseId, String search, User teacher)
            throws ServiceException {

        try {
            logger.info("Searching for users on courseId - {}, by search request - {}",
                    courseId, search);
            handler.beginNoTransaction(userDao, courseDao);
            List<CourseUser> courseUsers = new ArrayList<>();
            List<User> users = userDao.readByCourseId(start, recOnPage, courseId, search);
            for (User user : users) {
                CourseUser courseUser = courseUserDao.readById(courseId, user.getId());
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
    public CourseUser findCourseUser(long courseId, long userId) throws ServiceException {

        logger.info("Searching for course user, course id - {}, user id - {}",
                courseId, userId);
        try {
            handler.beginNoTransaction(courseUserDao, userDao, courseCategoryDao, courseDao);
            CourseUser courseUser = courseUserDao.readById(courseId, userId);
            if (courseUser != null) {
                Course course = courseDao.readById(courseUser.getCourse().getId());
                courseUser.setCourse(course);
                User user = userDao.readById(courseUser.getUser().getId());
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
    public boolean updateCourseUser(CourseUser courseUser, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            checkIfAvailableCourseData(courseUser.getCourse().getId(), teacher);
            logger.info("Updating course user, id - {}", courseUser.getId());
            boolean isUpdated;
            handler.begin(courseUserDao);
            isUpdated = courseUserDao.update(courseUser);
            handler.commit();
            return isUpdated;
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public boolean deleteCourseUser(CourseUser courseUser, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            checkIfAvailableCourseData(courseUser.getCourse().getId(), teacher);
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

    @Override
    public boolean deleteUserFromCourse(CourseUser courseUser, User teacher)
            throws ServiceException, CourseAccessDeniedException {

        try {
            checkIfAvailableCourseData(courseUser.getCourse().getId(), teacher);
            logger.info("Deleting user from course, course id - {}, user id - {}",
                    courseUser.getCourse().getId(), courseUser.getUser().getId());
            boolean isDeleted;
            handler.begin(courseDao, courseUserDao);
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

    @Override
    public Integer countAllCourseUsers(long courseId) throws ServiceException {

        logger.info("Counting all course users, course id - {}", courseId);
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
    public Integer countAllCourseUsers(long courseId, String search) throws ServiceException {

        logger.info("Counting all users, course id - {}, search request - {}",
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

    private void checkIfAvailableCourseData(long courseId, User teacher)
            throws CourseAccessDeniedException, DaoException {

        logger.info("Defining if teacher can view course data course id - {}, teacher id - {}",
                courseId, teacher.getId());
        try {
            handler.beginNoTransaction(courseDao);
            Course course = courseDao.readById(courseId);
            if (course == null || !(course.getOwner().getId().equals(teacher.getId())
                    || teacher.getRole().equals(UserRole.ADMIN))) {
                throw new CourseAccessDeniedException(String.format(ACCESS_ERROR, teacher.getId()));
            }
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

    private boolean isCourseExist(String courseName, long teacherId) throws DaoException {

        logger.info("Finding course of teacher with id - {}, with name - {}", teacherId, courseName);
        boolean result = false;
        Course course = courseDao.readByOwnerIdAndName(teacherId, courseName);
        if (course != null) {
            result = true;
        }
        return result;
    }
}
