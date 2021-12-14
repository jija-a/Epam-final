package by.alex.testing.service.impl;

import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.service.CommonService;
import by.alex.testing.service.HashService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommonServiceImpl implements CommonService {

    private static final Logger logger =
            LoggerFactory.getLogger(CommonServiceImpl.class);

    private static final CommonService instance = new CommonServiceImpl();

    public static CommonService getInstance() {
        return instance;
    }

    private final TransactionHandler handler;
    private final UserDao userDao;
    private final CourseCategoryDao courseCategoryDao;
    private final CourseDao courseDao;
    private final CourseUserDao courseUserDao;

    private CommonServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = factory.getUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.courseDao = factory.getCourseDao();
        this.courseUserDao = factory.getCourseUserDao();
        this.handler = new TransactionHandler();

    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        logger.info("Searching user by login: {}", login);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> register(User user) throws ServiceException {
        logger.debug("Registering user");
        List<String> errors = UserValidator.validate(user);

        if (errors.isEmpty()) {
            try {
                handler.begin(userDao);

                char[] password = user.getPassword();
                String hashedPsw = HashService.hash(password);
                user.setPassword(hashedPsw.toCharArray());
                userDao.save(user);
                handler.commit();
            } catch (DaoException e) {
                handler.rollback();
                throw new ServiceException(e.getMessage(), e);
            } finally {
                handler.endNoTransaction();
            }
        }
        return errors;
    }

    @Override
    public User login(String login, String password) throws ServiceException {

        logger.info("Logging In user, login - {}", login);
        try {
            handler.beginNoTransaction(userDao);
            User userByLogin = userDao.readByLogin(login);
            if (userByLogin != null &&
                    HashService.check(password, userByLogin.getPassword())) {
                return userByLogin;
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public User findUserById(Long id) throws ServiceException {
        logger.info("Searching user by id: {}", id);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public boolean updateUserProfile(User user) throws ServiceException {

        logger.info("Updating user profile, user id - {}", user.getId());
        try {
            handler.begin(userDao);
            userDao.update(user);
            handler.commit();
            return true;
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public List<User> findAllUsers(int start, int total) throws ServiceException {

        logger.info("Searching for all users, start - {}, rec on page - {}", start, total);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readAll(start, total);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllUsers() throws ServiceException {
        logger.info("Counting all users");
        try {
            handler.beginNoTransaction(userDao);
            return userDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<User> findUsersByName(int start, int total, String search)
            throws ServiceException {

        logger.info("Searching for user by name, start - {}, total - {}, search - {}",
                start, total, search);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByNameOrLogin(start, total, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllUsers(String search) throws ServiceException {
        logger.info("Counting all users by search request - {}", search);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.count(search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseCategory> readAllCourseCategories() throws ServiceException {
        logger.info("Reading all course categories");
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<Course> readCourseByTitle(int start, int recOnPage, String title) throws ServiceException {
        logger.debug("Reading course by title, start - {}, rerOnPage - {}, title - {}",
                start, recOnPage, title);

        try {
            handler.beginNoTransaction(courseDao);
            List<Course> courses = courseDao.findCourseByTitle(title, start, recOnPage);
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
        logger.info("Counting courses, search req: {}", search);
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
        logger.info("Reading all courses, start - {}, rec on page - {}", start, recOnPage);
        try {
            handler.beginNoTransaction(courseUserDao, courseDao, courseCategoryDao, userDao);
            List<Course> courses = courseDao.findAll(start, recOnPage);
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
    public Course readCourseById(long courseId) throws ServiceException {
        logger.info("Reading course by id - {}", courseId);
        try {
            handler.beginNoTransaction(courseDao);
            return courseDao.findOne(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    private void setCourseLinks(Course course) throws DaoException {
        User user = userDao.findOne(course.getOwner().getId());
        CourseCategory category = courseCategoryDao.findOne(course.getCategory().getId());
        course.setOwner(user);
        course.setCategory(category);
    }
}
