package by.alex.testing.service;

import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;

import java.util.List;

public interface CourseUserService {

    List<User> findCourseUsers(int start, int recOnPage, long courseId)
            throws ServiceException;

    List<User> findCourseUsersByName(int start, int recOnPage, long courseId, String search)
            throws ServiceException;

    Integer countAllCourseUsers(long courseId) throws ServiceException;

    Integer countAllCourseUsers(long courseId, String search) throws ServiceException;

    void update(CourseUser courseUser) throws ServiceException;

    void create(CourseUser courseUser) throws ServiceException;

    List<CourseUser> findRequestsOnCourse(long userId) throws ServiceException;

    CourseUser findCourseUser(long courseId, long userId) throws ServiceException;

    void delete(CourseUser courseUser) throws ServiceException;
}
