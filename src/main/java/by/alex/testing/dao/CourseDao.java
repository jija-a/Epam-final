package by.alex.testing.dao;

import by.alex.testing.domain.Course;

import java.util.List;

public interface CourseDao {

    List<Course> readCourseByTitle(String title, int start, int recOnPage) throws DaoException;

    List<Course> readByOwnerId(Long userId) throws DaoException;

    List<Course> readAll(int start, int recOnPage) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;
}
