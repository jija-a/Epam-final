package by.alex.testing.dao;

import by.alex.testing.domain.CourseCategory;

import java.util.List;

/**
 * Dao interface for {@link CourseCategory}.
 */
public interface CourseCategoryDao extends BaseDao<CourseCategory> {

    /**
     * Method finds all {@link CourseCategory}'s in DB, uses limit
     * from start to recordsPerPage.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link CourseCategory}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<CourseCategory> findAll(int start, int recordsPerPage)
            throws DaoException;

    /**
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param search         {@link CourseCategory} title
     * @return {@link List} of {@link CourseCategory}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<CourseCategory> findByTitle(int start,
                                     int recordsPerPage,
                                     String search)
            throws DaoException;

    /**
     * Method counts all {@link CourseCategory}'s in DB.
     *
     * @return {@link Integer} quantity of {@link CourseCategory} in DB
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer count() throws DaoException;

    /**
     * Method counts all {@link CourseCategory}'s in DB by title.
     *
     * @param search {@link CourseCategory} title
     * @return {@link Integer} quantity of {@link CourseCategory} in DB
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer count(String search) throws DaoException;

    /**
     * Method allows finding out if {@link CourseCategory} with
     * certain title exists in DB.
     *
     * @param title {@link CourseCategory} title
     * @return true if exists, otherwise - false
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find out if entity exists
     */
    boolean exists(String title) throws DaoException;
}
