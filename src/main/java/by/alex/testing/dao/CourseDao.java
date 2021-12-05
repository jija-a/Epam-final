package by.alex.testing.dao;

import by.alex.testing.domain.Course;

import java.util.List;

public interface CourseDao {

    List<Course> readCourseByTitle(String title, int start, int recOnPage) throws DaoException;

    List<Course> readByOwnerId(Long userId, int start, int recOnPage) throws DaoException;

    Course readByOwnerIdAndName(long teacherId, String courseName) throws DaoException;

    List<Course> readAll(int start, int recOnPage) throws DaoException;

    List<Course> readExcludingUserCourses(long studentId, int start, int recordsPerPage) throws DaoException;

    List<Course> readExcludingUserCourses(long studentId, int start, int recordsPerPage, String search) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;

    Integer countOwnerCourses(long userId) throws DaoException;

    int countAvailableCourses(long studentId) throws DaoException;

    int countAvailableCourses(long studentId, String search) throws DaoException;
}
