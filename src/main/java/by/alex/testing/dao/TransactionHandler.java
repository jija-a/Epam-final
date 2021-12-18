package by.alex.testing.dao;

import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.dao.pool.ProxyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public final class TransactionHandler {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TransactionHandler.class);

    /**
     * @see ProxyConnection
     */
    private ProxyConnection connection;

    /**
     * Method initializes {@link ConnectionPool}.
     *
     * @see ConnectionPool
     */
    public static void init() {
        ConnectionPool.getInstance();
    }

    /**
     * Method set {@link ProxyConnection} in {@link BaseDao}
     * implementation. And then they can use one general connection.
     * After action in DAO needs to be called endNoTransaction() method.
     *
     * @param dao  {@link BaseDao} implementation
     * @param daos optional {@link BaseDao}'s implementations
     * @see java.sql.Connection
     * @see BaseDao
     */
    public void beginNoTransaction(final BaseDao dao, final BaseDao... daos) {
        if (connection == null) {
            connection = (ProxyConnection)
                    ConnectionPool.getInstance().getConnection();
        }
        dao.setConnection(connection);
        for (BaseDao entity : daos) {
            entity.setConnection(connection);
        }
    }

    /**
     * Method set {@link ProxyConnection} in {@link BaseDao}
     * implementation. And then they can use one general connection.
     * Then connection set auto commit on false. To complete changes
     * in DB connection needs to be committed, or rollback if
     * exception was thrown.
     *
     * @param dao  {@link BaseDao} implementation
     * @param daos optional {@link BaseDao}'s implementations
     * @see java.sql.Connection
     * @see BaseDao
     */
    public void begin(final BaseDao dao, final BaseDao... daos) {
        if (connection == null) {
            connection = (ProxyConnection)
                    ConnectionPool.getInstance().getConnection();
        }
        dao.setConnection(connection);
        for (BaseDao entity : daos) {
            entity.setConnection(connection);
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error(" Error executing query ", e);
        }
    }

    /**
     * Method ends transaction. Setting connection auto commit on true.
     * Set connection eq to null.
     *
     * @see java.sql.Connection
     */
    public void end() {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                LOGGER.info("Transaction ended");
            } catch (SQLException e) {
                LOGGER.error("Error changing autocommit status", e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
            connection = null;
        }
    }

    /**
     * Method ends no transaction. Set connection eq to null.
     *
     * @see java.sql.Connection
     */
    public void endNoTransaction() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
            connection = null;
            LOGGER.info("No transaction ended");
        }
    }

    /**
     * Method commit changes in DB.
     *
     * @see java.sql.Connection
     */
    public void commit() {
        try {
            connection.commit();
            LOGGER.info("Connection committed");
        } catch (SQLException e) {
            LOGGER.error("Commit transaction error", e);
        }
    }

    /**
     * Method rollback all changes that was in transaction.
     *
     * @see java.sql.Connection
     */
    public void rollback() {
        try {
            connection.rollback();
            LOGGER.info("Connection rollback");
        } catch (SQLException e) {
            LOGGER.error("Rollback transaction error", e);
        }
    }
}
