package by.alex.testing.service.impl;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.UnknownEntityException;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.validator.CourseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class CourseServiceImpl implements CourseService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CourseServiceImpl.class);

    /**
     * Error message if {@link Course} already exists.
     *
     * @see MessageManager
     * @see MessageConstant
     */
    private static final String EXISTS_ERROR =
            MessageManager.INSTANCE.getMessage(MessageConstant.ALREADY_EXISTS);

    /**
     * Error message if {@link Course} can't be created.
     *
     * @see MessageManager
     * @see MessageConstant
     */
    private static final String CREATION_ERROR =
            MessageManager.INSTANCE.getMessage(MessageConstant.CREATE_ERROR);

    /**
     * {@link CourseServiceImpl} instance. Singleton pattern.
     */
    private static final CourseService SERVICE = new CourseServiceImpl();

    /**
     * @return {@link CourseServiceImpl} instance
     */
    public static CourseService getInstance() {
        return SERVICE;
    }

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    /**
     * @see CourseCategoryDao
     */
    private final CourseCategoryDao courseCategoryDao;

    /**
     * @see CourseDao
     */
    private final CourseDao courseDao;

    /**
     * @see CourseUserDao
     */
    private final CourseUserDao courseUserDao;

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    private CourseServiceImpl() {
        DaoFactory daoFactory =
                DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = daoFactory.getUserDao();
        this.courseCategoryDao = daoFactory.getCourseCategoryDao();
        this.courseDao = daoFactory.getCourseDao();
        this.courseUserDao = daoFactory.getCourseUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Course> findAllCourses(final int start,
                                       final int recordsPerPage)
            throws ServiceException {
        LOGGER.info("Searching all courses, start: {}, rec: {}",
                start, recordsPerPage);
        try {
            handler.beginNoTransaction(courseUserDao, courseDao,
                    courseCategoryDao, userDao);
            List<Course> courses = courseDao.findAll(start, recordsPerPage);
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
    public List<Course> findCoursesByTitle(final int start,
                                           final int recordsPerPage,
                                           final String title)
            throws ServiceException {
        LOGGER.info("Searching courses by title, start: {}, rec: {}, title: {}",
                start, recordsPerPage, title);
        try {
            handler.beginNoTransaction(courseDao);
            List<Course> courses =
                    courseDao.findCourseByTitle(title, start, recordsPerPage);
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
    public Course findCourse(final long id) throws ServiceException {
        LOGGER.info("Searching course by id: {}", id);
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Course> findAllCoursesByTeacherId(final int start,
                                                  final int recordsPerPage,
                                                  final long teacherId)
            throws ServiceException {
        LOGGER.info("Searching all courses, start: {}, rec: {}, teacher id: {}",
                start, recordsPerPage, teacherId);
        try {
            handler.beginNoTransaction(courseDao, userDao, courseCategoryDao);
            List<Course> courses =
                    courseDao.findByOwnerId(teacherId, start, recordsPerPage);
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
    public List<String> createCourse(final Course course)
            throws ServiceException, UnknownEntityException {
        LOGGER.info("Creating course");
        List<String> errors = CourseValidator.validate(course);
        if (errors.isEmpty()) {
            String name = course.getName();
            long ownerId = course.getOwner().getId();
            long categoryId = course.getCategory().getId();
            try {
                handler.begin(courseDao, courseCategoryDao);
                if (courseCategoryDao.findOne(categoryId) == null) {
                    throw new UnknownEntityException("Unknown category id: "
                            + categoryId);
                }
                if (isCourseExist(name, ownerId)) {
                    errors.add(EXISTS_ERROR);
                } else {
                    if (!courseDao.save(course)) {
                        errors.add(CREATION_ERROR);
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
    public Integer countAllCourses() throws ServiceException {
        LOGGER.info("Counting courses");
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
    public Integer countAllCourses(final String search)
            throws ServiceException {
        LOGGER.info("Counting courses, search req: {}", search);
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
    public int countAllRequests(final long teacherId) throws ServiceException {
        LOGGER.info("Counting teacher courses requests, teacher id - {}",
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
    public int countStudentCourses(final long id)
            throws ServiceException {
        LOGGER.info("Counting student courses, student id: {}", id);
        try {
            handler.beginNoTransaction(courseUserDao);
            return courseUserDao.countStudentCourses(id,
                    UserCourseStatus.ON_COURSE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCoursesByTeacherId(final long id)
            throws ServiceException {
        LOGGER.info("Counting all courses by teacher id: {}", id);
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.countOwnerCourses(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseUser> findRequestsOnCourse(final int start,
                                                 final int recordsPerPage,
                                                 final long teacherId)
            throws ServiceException {
        LOGGER.info("Searching for courses requests, teacher id: {}",
                teacherId);
        try {
            handler.beginNoTransaction(courseUserDao, userDao,
                    courseCategoryDao, courseDao);
            List<CourseUser> courseUsers =
                    courseUserDao.findRequests(start, recordsPerPage,
                            teacherId);
            for (CourseUser courseUser : courseUsers) {
                Course course =
                        courseDao.findOne(courseUser.getCourse().getId());
                courseUser.setCourse(course);
                User user = userDao.findOne(courseUser.getUser().getId());
                courseUser.setUser(user);
            }
            return courseUsers;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    private void setCourseLinks(final Course course) throws DaoException {
        User user = userDao.findOne(course.getOwner().getId());
        CourseCategory category =
                courseCategoryDao.findOne(course.getCategory().getId());
        course.setOwner(user);
        course.setCategory(category);
    }

    private boolean isCourseExist(final String courseName, final long teacherId)
            throws DaoException {
        LOGGER.info("Finding course, teacher id: {}, course name: {}",
                teacherId, courseName);

        boolean result = false;
        Course course = courseDao.findByOwnerIdAndTitle(teacherId, courseName);
        if (course != null) {
            result = true;
        }
        return result;
    }
}
