package by.alex.testing.service;

import by.alex.testing.domain.Quiz;

import java.util.List;

public interface TestService {

    Quiz readTestById(long testId) throws ServiceException;

    void updateTestInfo(Quiz quiz) throws ServiceException;

    void createTest(Quiz quiz) throws ServiceException;

}
