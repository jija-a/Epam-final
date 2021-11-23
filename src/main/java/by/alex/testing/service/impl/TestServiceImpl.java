package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.TestDaoImpl;
import by.alex.testing.domain.Quiz;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;
import by.alex.testing.service.validator.TestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestServiceImpl implements TestService {

    private static final Logger logger =
            LoggerFactory.getLogger(TestServiceImpl.class);

    private static final TestService instance = new TestServiceImpl();

    public static TestService getInstance() {
        return instance;
    }

    private final TransactionHandler handler;
    private final TestDaoImpl testDao;

    private TestServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.testDao = factory.getTestDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<Quiz> readAllCourseTests(int start, int recOnPage, long courseId)
            throws ServiceException {

        logger.info("Searching tests by course id: {}", courseId);
        try {
            handler.beginNoTransaction(testDao);
            return testDao.readAllTestsByCourseId(courseId, start, recOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Quiz readTestById(long testId) throws ServiceException {
        logger.info("Searching test by id: {}", testId);
        try {
            handler.beginNoTransaction(testDao);
            return testDao.readById(testId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void deleteTest(long id) throws ServiceException {

        logger.info("Deleting test by id: {}", id);
        try {
            handler.begin(testDao);
            testDao.delete(id);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

    @Override
    public Integer countAllTests(long courseId) throws ServiceException {

        logger.info("Counting users");
        try {
            handler.beginNoTransaction(testDao);
            return testDao.count(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateTest(Quiz test) throws ServiceException {

        logger.info("Updating test");
        List<String> errors = TestValidator.validate(test);

        if (errors.isEmpty()) {
            try {
                handler.begin(testDao);
                testDao.update(test);
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

}
