package by.alex.testing.dao;

import by.alex.testing.domain.CourseCategory;

import java.util.List;

public interface CourseCategoryDao {

    List<CourseCategory> readAll(int start, int recOnPage) throws DaoException;

    CourseCategory readByTitle(String name) throws DaoException;

    List<CourseCategory> readByTitle(int start, int recOnPage, String search) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;
}
