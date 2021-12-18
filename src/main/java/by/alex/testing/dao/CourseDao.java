package by.alex.testing.dao;

import by.alex.testing.domain.Course;

import java.util.List;

/**
 * Dao interface for {@link Course}.
 */
public interface CourseDao extends BaseDao<Course> {

    /**
     * Method to find {@link Course}'s in DB from start to recPerPage.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Course> findAll(int start, int recordsPerPage) throws DaoException;

    /**
     * Method to find not {@link by.alex.testing.domain.User}
     * {@link Course}'s in DB from start to recPerPage.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Course> findNotUserCourses(long studentId,
                                    int start,
                                    int recordsPerPage)
            throws DaoException;

    /**
     * Method to find not {@link by.alex.testing.domain.User}
     * {@link Course}'s by title in DB from start to recPerPage.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param search         {@link Course} title
     * @return {@link List} of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Course> findNotUserCourses(long studentId,
                                    int start,
                                    int recordsPerPage,
                                    String search)
            throws DaoException;

    /**
     * Method to find all {@link by.alex.testing.domain.User}
     * {@link Course}'s that they created.
     *
     * @param userId         {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Course> findByOwnerId(long userId, int start, int recordsPerPage)
            throws DaoException;

    /**
     * Method allow finding all {@link Course}'s by title.
     *
     * @param title          {@link Course} title
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Course> findCourseByTitle(String title,
                                   int start,
                                   int recordsPerPage)
            throws DaoException;

    /**
     * Method allow finding all {@link by.alex.testing.domain.User}
     * {@link Course}'s that they created by {@link Course} title.
     *
     * @param teacherId {@link by.alex.testing.domain.User} id
     * @param title     {@link Course} title
     * @return {@link Course}
     * @throws DaoException if {@link java.sql.SQLException}
     *                      was thrown
     *                      when tries to find entity
     */
    Course findByOwnerIdAndTitle(long teacherId, String title)
            throws DaoException;

    /**
     * Method allow to count all {@link Course}'s.
     *
     * @return {@link Integer} quantity of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer count() throws DaoException;

    /**
     * Method allow to count all {@link Course}'s by title.
     *
     * @param title {@link Course} title
     * @return {@link Integer} quantity of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer count(String title) throws DaoException;

    /**
     * Method allows to count all {@link Course}'s that
     * {@link by.alex.testing.domain.User} created.
     *
     * @param userId {@link by.alex.testing.domain.User} id
     * @return {@link Integer} quantity of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer countOwnerCourses(long userId) throws DaoException;

    /**
     * Method allows to count all {@link Course}'s that
     * {@link by.alex.testing.domain.User} does not enter.
     *
     * @param studentId {@link by.alex.testing.domain.User} id
     * @return {@link Integer} quantity of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer countAvailableCourses(long studentId) throws DaoException;

    /**
     * Method allows to count all {@link Course}'s by title that
     * {@link by.alex.testing.domain.User} does not enter.
     *
     * @param studentId {@link by.alex.testing.domain.User} id
     * @param title     {@link Course} title
     * @return {@link Integer} quantity of {@link Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer countAvailableCourses(long studentId, String title)
            throws DaoException;
}
