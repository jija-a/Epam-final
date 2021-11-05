package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.Quiz;
import by.alex.testing.domain.User;

import java.util.List;

public interface CourseService {

    List<Course> readCourseByTitle(String title) throws ServiceException;

    List<User> readUsersByCourseId(Long id) throws ServiceException;

    List<Quiz> readAllTestsByCourseName(long courseId) throws ServiceException;

    void removeTestFromCourse(long testId) throws ServiceException;

    void removeUserFromCourse(long userId, long courseId) throws ServiceException;

    Course readCourseById(long courseId) throws ServiceException;
}
