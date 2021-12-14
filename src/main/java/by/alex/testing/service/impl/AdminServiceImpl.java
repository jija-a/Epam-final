package by.alex.testing.service.impl;

import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.AdminService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.validator.CourseCategoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminServiceImpl.class);

    private static final AdminService instance = new AdminServiceImpl();

    public static AdminService getInstance() {
        return instance;
    }

    private final TransactionHandler handler;
    private final UserDao userDao;
    private final CourseCategoryDao courseCategoryDao;

    private AdminServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = factory.getUserDao();
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public void deleteUser(long userId) throws ServiceException {
        logger.info("Deleting user, user id - {}", userId);

        try {
            handler.begin(userDao);
            userDao.delete(userId);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public List<CourseCategory> readAllCourseCategories(int start, int recOnPage)
            throws ServiceException {
        logger.info("Reading all course categories, start - {}, records on page - {}", start, recOnPage);

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.findAll(start, recOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public CourseCategory readCategoryById(long categoryId)
            throws ServiceException {
        logger.info("Reading category by id - {}", categoryId);

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.findOne(categoryId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseCategory> readAllCourseCategories(int start, int recOnPage, String search)
            throws ServiceException {
        logger.info("Reading all course categories, start - {}, rec on page - {}, search - {}",
                start, recOnPage, search);

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.findByTitle(start, recOnPage, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseCategories(String search) throws ServiceException {

        logger.info("Counting all course categories by search - {}", search);
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.count(search);
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateCategory(CourseCategory category)
            throws ServiceException {
        logger.info("Updating category, id - {}", category.getId());

        List<String> errors = CourseCategoryValidator.validate(category);
        if (errors.isEmpty()) {
            try {
                handler.begin(courseCategoryDao);
                courseCategoryDao.update(category);
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
    public List<String> create(CourseCategory category) throws ServiceException {
        logger.info("Creating category");

        List<String> errors = CourseCategoryValidator.validate(category);
        if (errors.isEmpty()) {
            try {
                handler.begin(courseCategoryDao);
                courseCategoryDao.save(category);
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
    public boolean isCategoryExists(String title) throws ServiceException {
        logger.info("Reading category by title - {}", title);
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.exists(title);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseCategories() throws ServiceException {
        logger.info("Counting all course categories");

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void deleteCourseCategory(long id) throws ServiceException {
        logger.info("Deleting course category, id - {}", id);

        try {
            handler.begin(courseCategoryDao);
            courseCategoryDao.delete(id);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }
}
