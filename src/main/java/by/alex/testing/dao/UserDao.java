package by.alex.testing.dao;

import by.alex.testing.domain.User;

import java.util.List;

/**
 * Dao interface for {@link User}.
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Method allows finding {@link User}'s from start to
     * record per page.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link User}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<User> findAll(int start, int recordsPerPage) throws DaoException;

    /**
     * Method allows finding {@link User} by login.
     *
     * @param login {@link User} login
     * @return {@link User}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    User findByLogin(String login) throws DaoException;

    /**
     * Method allows finding {@link User}'s by name or login.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param search         {@link User} name or login
     * @return {@link List} of {@link User}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<User> findBySearchRequest(int start, int recordsPerPage, String search)
            throws DaoException;

    /**
     * Method allows finding {@link User}'s on
     * certain {@link by.alex.testing.domain.Course}.
     *
     * @param id {@link by.alex.testing.domain.Course} id
     * @return {@link List} of {@link User}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<User> findByCourseId(Long id) throws DaoException;

    /**
     * Method allows finding {@link User}'s on
     * certain {@link by.alex.testing.domain.Course}
     * from start to record per page.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param courseId       {@link by.alex.testing.domain.Course} id
     * @return {@link List} of {@link User}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<User> findByCourseId(int start, int recordsPerPage, long courseId)
            throws DaoException;

    /**
     * Method allows finding {@link User}'s by login or name on
     * certain {@link by.alex.testing.domain.Course}
     * from start to record per page.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param courseId       {@link by.alex.testing.domain.Course} id
     * @param search         {@link User} name or login
     * @return {@link List} of {@link User}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<User> findByCourseId(int start, int recordsPerPage,
                              long courseId, String search)
            throws DaoException;

    /**
     * Method allows counting all {@link User}'s in DB.
     *
     * @return {@link Integer} {@link User}'s quantity
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    Integer count() throws DaoException;

    /**
     * Method allows counting all {@link User}'s in DB by name or login.
     *
     * @param search {@link User} name or login
     * @return {@link Integer} {@link User}'s quantity
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    Integer count(String search) throws DaoException;
}
