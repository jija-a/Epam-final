package by.alex.testing.service.impl;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.CourseCategoryService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.validator.CourseCategoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class CourseCategoryServiceImpl implements CourseCategoryService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CourseCategoryServiceImpl.class);

    /**
     * {@link CourseCategoryServiceImpl} instance. Singleton pattern.
     */
    private static final CourseCategoryService SERVICE =
            new CourseCategoryServiceImpl();

    /**
     * @return {@link CourseCategoryServiceImpl} instance
     */
    public static CourseCategoryService getInstance() {
        return SERVICE;
    }

    /**
     * @see CourseCategoryDao
     */
    private final CourseCategoryDao courseCategoryDao;

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    private CourseCategoryServiceImpl() {
        DaoFactory daoFactory =
                DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.courseCategoryDao = daoFactory.getCourseCategoryDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<String> create(final CourseCategory category)
            throws ServiceException {
        LOGGER.info("Creating category");
        List<String> errors = CourseCategoryValidator.validate(category);
        if (this.isCategoryExists(category.getName())) {
            errors.add(MessageManager.INSTANCE
                    .getMessage(MessageConstant.ALREADY_EXISTS));
        } else {
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
        }
        return errors;
    }

    @Override
    public List<CourseCategory> findAllCategories(final int start,
                                                  final int recOnPage)
            throws ServiceException {
        LOGGER.info("Searching course categories, start: {}, rec: {}",
                start, recOnPage);

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
    public List<CourseCategory> findAllCategories()
            throws ServiceException {
        LOGGER.info("Searching all course categories");
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
    public List<CourseCategory> findAllCategories(final int start,
                                                  final int recordsPerPage,
                                                  final String search)
            throws ServiceException {
        LOGGER.info("Searching categories, start: {}, rec: {}, search: {}",
                start, recordsPerPage, search);
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.findByTitle(start, recordsPerPage, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public CourseCategory findCategory(final long id)
            throws ServiceException {
        LOGGER.info("Searching category by id: {}", id);
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateCategory(final CourseCategory category)
            throws ServiceException {
        LOGGER.info("Updating category, id: {}", category.getId());
        List<String> errors = CourseCategoryValidator.validate(category);
        if (this.isCategoryExists(category.getName())) {
            errors.add(MessageManager.INSTANCE
                    .getMessage(MessageConstant.ALREADY_EXISTS));
        } else {
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
        }
        return errors;
    }

    @Override
    public void deleteCategory(final long id) throws ServiceException {
        LOGGER.info("Deleting category, id: {}", id);
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

    @Override
    public Integer countCategories() throws ServiceException {
        LOGGER.info("Counting categories");
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
    public Integer countCategories(final String search)
            throws ServiceException {
        LOGGER.info("Counting categories by req: {}", search);
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

    private boolean isCategoryExists(final String title)
            throws ServiceException {
        try {
            handler.beginNoTransaction(courseCategoryDao);
            return courseCategoryDao.exists(title);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }
}
