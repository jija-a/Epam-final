package by.alex.testing.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T> {

    boolean save(T t) throws DaoException;

    T findOne(long id) throws DaoException;

    List<T> findAll() throws DaoException;

    boolean update(T t) throws DaoException;

    boolean delete(long id) throws DaoException;

    void setConnection(Connection connection);

    void close(Statement st);
}
