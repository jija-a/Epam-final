package by.alex.testing.dao;

import by.alex.testing.domain.CourseUser;

import java.util.List;

public interface CourseUserDao {

    CourseUser readById(long courseId, long userId) throws DaoException;

    List<CourseUser> readAllUserCourses(Long userId) throws DaoException;

    List<CourseUser> readAllRequests(int start, int recOnPage, long userId) throws DaoException;

    boolean delete(CourseUser courseUser) throws DaoException;

    Integer count(long courseId) throws DaoException;

    Integer count(long courseId, String search) throws DaoException;

    int countRequests(long teacherId) throws DaoException;
}
