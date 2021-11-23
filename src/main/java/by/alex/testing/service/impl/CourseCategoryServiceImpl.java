package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.CourseCategoryDaoImpl;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.validator.CourseCategoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CourseCategoryServiceImpl implements CourseCategoryService {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseCategoryServiceImpl.class);

    private static final CourseCategoryService instance = new CourseCategoryServiceImpl();

    public static CourseCategoryService getInstance() {
        return instance;
    }

    private final CourseCategoryDaoImpl courseCategoryDao;
    private final TransactionHandler handler;

    private CourseCategoryServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseCategoryDao = factory.getCourseCategoryDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<CourseCategory> readAllCourseCategories() throws ServiceException {
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseCategory> readAllCourseCategories(int start, int recOnPage) throws ServiceException {
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.readAll(start, recOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public CourseCategory readCategoryById(long categoryId) throws ServiceException {
        logger.info("Reading category by id - {}", categoryId);
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.readById(categoryId);
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<CourseCategory> readCourseCategoriesByTitle(int start, int recOnPage, String search)
            throws ServiceException {

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.readByTitle(start, recOnPage, search);
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllCourseCategories(String search) throws ServiceException {

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.count(search);
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateCategory(CourseCategory category) throws ServiceException {
        logger.info("Updating category - {}", category);
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
        logger.info("Creating category - {}", category);
        List<String> errors = CourseCategoryValidator.validate(category);
        if (errors.isEmpty()) {
            try {
                handler.begin(courseCategoryDao);
                courseCategoryDao.create(category);
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
    public Integer countAllCourseCategories() throws ServiceException {

        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.count();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void deleteCourseCategory(long id) throws ServiceException {
        try {
            handler.begin(courseCategoryDao);
            courseCategoryDao.delete(id);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException("DAO layer provided exception: ", e);
        } finally {
            handler.end();
        }
    }
}
