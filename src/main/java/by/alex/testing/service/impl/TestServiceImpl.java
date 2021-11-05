package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.dao.mysql.TestDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.Quiz;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;

import java.sql.Connection;
import java.util.List;

public class TestServiceImpl implements TestService {

    @Override
    public Quiz readTestById(long testId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        Quiz quiz;
        try {
            quiz = testDao.readById(testId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
        return quiz;
    }

    @Override
    public void updateTestInfo(Quiz quiz) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        try {
            testDao.update(quiz);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public void createTest(Quiz quiz) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        try {
            testDao.create(quiz);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }
}
