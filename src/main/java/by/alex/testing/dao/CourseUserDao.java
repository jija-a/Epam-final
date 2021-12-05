package by.alex.testing.dao;

import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.service.ServiceException;

import java.util.List;

public interface CourseUserDao {

    CourseUser readById(long courseId, long userId) throws DaoException;

    List<CourseUser> readUserCoursesByStatus(Long userId, UserCourseStatus status) throws DaoException;

    List<CourseUser> readUserCoursesByStatus(long studentId, UserCourseStatus status, int start, int recordsPerPage) throws DaoException;

    List<CourseUser> readAllRequests(int start, int recOnPage, long userId) throws DaoException;

    boolean delete(CourseUser courseUser) throws DaoException;

    Integer count(long courseId) throws DaoException;

    Integer count(long courseId, String search) throws DaoException;

    int countRequests(long teacherId) throws DaoException;

    int countStudentCourses(long studentId, UserCourseStatus status) throws DaoException;
}
