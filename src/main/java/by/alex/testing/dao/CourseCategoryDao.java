package by.alex.testing.dao;

import by.alex.testing.domain.CourseCategory;

import java.util.List;

public interface CourseCategoryDao extends BaseDao<CourseCategory> {

    List<CourseCategory> findAll(int start, int recOnPage) throws DaoException;

    List<CourseCategory> findByTitle(int start, int recOnPage, String search) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;

    boolean exists(String title) throws DaoException;
}
