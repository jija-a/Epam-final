package by.alex.testing.dao;

import by.alex.testing.domain.CourseUser;

import java.util.List;

public interface CourseUserDao {

    List<CourseUser> readAllCourseUsers() throws DaoException;

    void removeUserFromCourse(CourseUser courseUser) throws DaoException;

    void addUserToCourse(CourseUser courseUser) throws DaoException;

    void updateCourseUser(CourseUser courseUser) throws DaoException;

    List<CourseUser> readAllUserCourses(Long userId) throws DaoException;
}
