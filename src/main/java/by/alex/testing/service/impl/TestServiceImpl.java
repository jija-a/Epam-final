package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.dao.mysql.TestDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.Test;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;

import java.sql.Connection;
import java.util.List;

public class TestServiceImpl implements TestService {

    @Override
    public List<Test> readAllTestsByCourseId(long courseId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        List<Test> tests;
        try {
            tests = testDao.readAllTestsByCourseId(courseId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
        return tests;
    }

    @Override
    public void removeTestFromCourse(long testId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        try {
            testDao.delete(testId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public Test readTestById(long testId) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        Test test;
        try {
            test = testDao.readById(testId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
        return test;
    }

    @Override
    public void updateTestInfo(Test test) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        try {
            testDao.update(test);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public void createTest(Test test) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        TestDao testDao = new TestDaoImpl(connection);
        try {
            testDao.create(test);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }
}
