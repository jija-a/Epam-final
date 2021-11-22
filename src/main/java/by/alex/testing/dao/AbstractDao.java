package by.alex.testing.dao;

import by.alex.testing.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDao<T extends Entity, K> {
    private static final Logger logger =
            LoggerFactory.getLogger(AbstractDao.class);

    protected Connection connection;

    public abstract List<T> readAll() throws DaoException;

    public abstract T readById(K id) throws DaoException;

    public abstract void create(T entity) throws DaoException;

    public abstract void update(T entity) throws DaoException;

    public abstract void delete(K id) throws DaoException;

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            logger.error("Statement can't be closed", e);
        }
    }
}
