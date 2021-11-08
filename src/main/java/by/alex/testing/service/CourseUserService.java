package by.alex.testing.service;

import by.alex.testing.domain.User;

import java.util.List;

public interface CourseUserService {

    void addUserOnCourse(long userId, long courseId) throws ServiceException;

    List<User> readUsersByCourseId(Long id) throws ServiceException;

    void removeUserFromCourse(long userId, long courseId) throws ServiceException;
}
