package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.dao.TestResultDao;
import by.alex.testing.dao.UserDao;
import by.alex.testing.dao.mysql.TestDaoImpl;
import by.alex.testing.dao.mysql.TestResultDaoImpl;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.Quiz;
import by.alex.testing.domain.TestResult;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class TestServiceImpl implements TestService {

    private static final Logger logger =
            LoggerFactory.getLogger(TestServiceImpl.class);

    private static final TestService instance = new TestServiceImpl();

    private TestResultDao testResultDao;
    private UserDao userDao;
    private TestDao testDao;

    public static TestService getInstance() {
        return instance;
    }

    private TestServiceImpl() {
    }

    @Override
    public List<TestResult> showUserResults(long userId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        testResultDao = new TestResultDaoImpl(connection);
        try {
            List<TestResult> results = testResultDao.readAllByUserId(userId);
            for (TestResult result : results) {
                this.setTestResultLinks(result, connection);
                this.calculateScore(result);
            }
            return results;
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        }
    }

    @Override
    public List<TestResult> showUserTests(long userId, int limit) throws ServiceException {
        return null;
    }

    @Override
    public List<Quiz> showUserNearestTests(long userId, int homePageTestsLimit) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        testDao = new TestDaoImpl(connection);
        try {
            return testDao.readAllTestsByUserIdSortedByDate(userId, homePageTestsLimit);
        } catch (DaoException e) {
            throw new ServiceException("DAO layer provided exception: ", e);
        }
    }

    private void calculateScore(TestResult result) {
        logger.info("Calculating user test core");
        double userPercent = result.getPercent();
        int maxScore = result.getTest().getMaxScore();
        long userScore = Math.round(maxScore * userPercent / 100);
        result.setScore(userScore);
    }

    private void setTestResultLinks(TestResult result, Connection connection) throws DaoException {
        logger.info("Mapping test result links");
        userDao = new UserDaoImpl(connection);
        testDao = new TestDaoImpl(connection);
        User user = userDao.readById(result.getUser().getId());
        Quiz test = testDao.readById(result.getTest().getId());
        result.setUser(user);
        result.setTest(test);
    }
}
