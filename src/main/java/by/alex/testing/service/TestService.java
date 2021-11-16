package by.alex.testing.service;

import by.alex.testing.domain.Quiz;
import by.alex.testing.domain.TestResult;

import java.util.List;

public interface TestService {

    List<TestResult> showUserResults(long userId) throws ServiceException;

    List<TestResult> showUserTests(long userId, int limit) throws ServiceException;

    List<Quiz> showUserNearestTests(long userId, int homePageTestsLimit) throws ServiceException;
}
