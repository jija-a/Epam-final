package by.alex.testing.service;

import by.alex.testing.domain.Test;

import java.util.List;

public interface TestService {

    List<Test> readAllTestsByCourseId(long courseId) throws ServiceException;

    void removeTestFromCourse(long testId) throws ServiceException;

    Test readTestById(long testId) throws ServiceException;

    void updateTestInfo(Test test) throws ServiceException;

    void createTest(Test test) throws ServiceException;


}
