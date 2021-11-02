package by.alex.testing.dao;

import by.alex.testing.domain.Entity;

import java.util.List;

public interface Dao<T extends Entity, K> {

    List<T> readAll() throws DaoException;

    T readById(K id) throws DaoException;

    void delete(K id) throws DaoException;

    void create(T t) throws DaoException;

    void update(T t) throws DaoException;
}
