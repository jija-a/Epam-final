package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;

import java.util.List;

public interface TestService {

    List<Quiz> readAllCourseTests(int start, int recOnPage, long courseId) throws ServiceException;

    Quiz readTestById(long testId) throws ServiceException;

    void deleteTest(long parseLong) throws ServiceException;

    Integer countAllTests(long courseId) throws ServiceException;

    List<String> updateTest(Quiz course) throws ServiceException;
}
