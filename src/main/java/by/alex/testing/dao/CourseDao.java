package by.alex.testing.dao;

import by.alex.testing.domain.Course;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {

    List<Course> findAll(int start, int recOnPage) throws DaoException;

    List<Course> findExcludingUserCourses(long studentId, int start, int recordsPerPage) throws DaoException;

    List<Course> findExcludingUserCourses(long studentId, int start, int recordsPerPage, String search) throws DaoException;

    List<Course> findByOwnerId(Long userId, int start, int recOnPage) throws DaoException;

    List<Course> findCourseByTitle(String title, int start, int recOnPage) throws DaoException;

    Course findByOwnerIdAndName(long teacherId, String courseName) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;

    Integer countOwnerCourses(long userId) throws DaoException;

    Integer countAvailableCourses(long studentId) throws DaoException;

    Integer countAvailableCourses(long studentId, String search) throws DaoException;
}
