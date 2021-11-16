package by.alex.testing.dao;

import by.alex.testing.domain.TestResult;

import java.util.List;

public interface TestResultDao extends Dao<TestResult, Long> {

    List<TestResult> readAllByTestId(long testId) throws DaoException;

    List<TestResult> readAllByUserId(long userId) throws DaoException;
}
