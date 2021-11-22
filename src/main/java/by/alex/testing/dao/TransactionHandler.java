package by.alex.testing.dao;

import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.dao.pool.ProxyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class TransactionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionHandler.class);

    private ProxyConnection connection;

    public TransactionHandler() {
    }

    public void beginNoTransaction(AbstractDao dao) {
        if (connection == null) {
            connection = (ProxyConnection) ConnectionPool.getInstance().getConnection();
        }
        dao.setConnection(connection);
    }

    public void begin(AbstractDao dao, AbstractDao... daos) {
        if (connection == null) {
            connection = (ProxyConnection) ConnectionPool.getInstance().getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(" Error executing query ", e);
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    public void end() {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Error changing autocommit status",  e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
            connection = null;
        }
    }

    public void endNoTransaction() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
            connection = null;
        }
    }
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("Commit transaction error", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Rollback transaction error", e);
        }
    }
}
