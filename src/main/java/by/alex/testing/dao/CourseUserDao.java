package by.alex.testing.dao;

import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.UserCourseStatus;

import java.util.List;

public interface CourseUserDao extends BaseDao<CourseUser> {

    CourseUser findByUserAndCourseId(long userId, long courseId) throws DaoException;

    List<CourseUser> findByUserIdAndStatus(Long userId, UserCourseStatus status) throws DaoException;

    List<CourseUser> findByUserIdAndStatus(long studentId, UserCourseStatus status, int start, int recordsPerPage) throws DaoException;

    List<CourseUser> readAllRequestsByTeacherId(int start, int recOnPage, long userId) throws DaoException;

    boolean delete(CourseUser courseUser) throws DaoException;

    Integer count(long courseId) throws DaoException;

    Integer count(long courseId, String search) throws DaoException;

    int countRequests(long teacherId) throws DaoException;

    int countStudentCourses(long studentId, UserCourseStatus status) throws DaoException;
}
