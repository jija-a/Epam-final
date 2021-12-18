package by.alex.testing.dao;

import by.alex.testing.domain.BaseEntity;

import java.sql.Connection;
import java.util.List;

/**
 * Data access layer pattern.
 *
 * @param <T> Generic that allows to create DAO only
 *            for classes that implements {@link BaseEntity}
 */
public interface BaseDao<T extends BaseEntity> {

    /**
     * Method allows save {@link T} in DB.
     *
     * @param t {@link T}
     * @return true if saved, otherwise - false
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to save entity
     */
    boolean save(T t) throws DaoException;

    /**
     * Method that finds {@link T} in DB by identity.
     *
     * @param id {@link Long} {@link T} identity
     * @return {@link T}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entity
     */
    T findOne(long id) throws DaoException;

    /**
     * Method that finds all {@link T} entities in DB.
     *
     * @return {@link List} of {@link T}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<T> findAll() throws DaoException;

    /**
     * Method that update {@link T} fields in DB.
     *
     * @param t {@link T}
     * @return true if updated, otherwise - false
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to update entity
     */
    boolean update(T t) throws DaoException;

    /**
     * Method that deletes {@link T} in DB by {@link Long} identity.
     *
     * @param id {@link Long} {@link T} identity
     * @return true if deleted, otherwise - false
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to delete entity
     */
    boolean delete(long id) throws DaoException;

    /**
     * Method set {@link Connection} to concrete implementation,
     * than that implementation uses it.
     *
     * @param connection {@link Connection}
     * @see Connection
     */
    void setConnection(Connection connection);
}
