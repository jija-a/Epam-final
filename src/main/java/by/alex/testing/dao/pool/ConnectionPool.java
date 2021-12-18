package by.alex.testing.dao.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Data access pattern. A database connection cache implementation.
 * Thread safe connection pool.
 *
 * @see BlockingQueue
 * @see ReentrantLock
 * @see AtomicBoolean
 * @see DatabaseConfig
 */

public final class ConnectionPool {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ConnectionPool.class);

    /**
     * {@link ConnectionPool} instance. Singleton pattern.
     */
    private static ConnectionPool instance;

    /**
     * @see BlockingQueue
     */
    private BlockingQueue<ProxyConnection> availableConnections;

    /**
     * @see Queue
     * @see ProxyConnection
     */
    private Queue<ProxyConnection> takenConnections;

    /**
     * {@link AtomicBoolean}, needed to synchronize method.
     *
     * @see AtomicBoolean
     */
    private static AtomicBoolean created = new AtomicBoolean(false);

    /**
     * {@link ReentrantLock}, needed to synchronize method.
     *
     * @see ReentrantLock
     */
    private static ReentrantLock lock = new ReentrantLock(true);

    /**
     * @see DatabaseConfig
     */
    private DatabaseConfig config;

    private ConnectionPool() {
        this.init();
    }

    /**
     * Thread safe method.
     *
     * @return {@link ConnectionPool} instance.
     */
    public static ConnectionPool getInstance() {
        if (!created.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    created.set(true);
                }
            } finally {
                lock.lock();
            }
        }
        return instance;
    }

    private void init() throws InitializingError {
        LOGGER.trace("Initializing connection pool");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new InitializingError("Initializing connection pool failed",
                    e);
        }
        initConnectionPool();
    }

    private void initConnectionPool() throws InitializingError {
        this.config = DatabaseConfig.getInstance();
        this.availableConnections =
                new ArrayBlockingQueue<>(config.getPoolSize());
        this.takenConnections = new ArrayDeque<>();
        for (int i = 0; i < config.getPoolSize(); i++) {
            try {
                availableConnections.add(this.createConnection());
                LOGGER.info("Connection {} added", i + 1);
            } catch (SQLException e) {
                throw new InitializingError("Database connection failed: ", e);
            }
        }
        if (availableConnections.isEmpty()) {
            throw new InitializingError("Pool isn't created");
        }
        checkConnectionsSize();
    }

    private void checkConnectionsSize() {
        int count = 0;
        int size = availableConnections.size();
        if (size != config.getPoolSize()) {
            while (size != config.getPoolSize()) {
                count++;
                try {
                    Connection connection = this.createConnection();
                    if (!availableConnections
                            .offer(new ProxyConnection(connection))) {
                        break;
                    }
                } catch (SQLException e) {
                    LOGGER.error("Error while creating connections", e);
                }
            }
            LOGGER.error("{} Connections have been lost and created again",
                    count);
        }
    }

    private ProxyConnection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                config.getUrl() + config.getDbName(),
                config.getUser(),
                config.getPassword());
        LOGGER.info("Connection created");
        return new ProxyConnection(connection);
    }

    /**
     * @return {@link Connection}, {@link ProxyConnection}
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            takenConnections.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Connection interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * @param connection {@link ProxyConnection}
     */
    public void releaseConnection(final Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            ProxyConnection con = (ProxyConnection) connection;
            takenConnections.remove(con);
            availableConnections.add(con);
        } else {
            LOGGER.warn("Can't return connection, possible connection leaking");
        }
    }

    /**
     * Method destroys all {@link Connection}'s.
     */
    public void destroyPool() {
        for (ProxyConnection con : this.availableConnections) {
            this.closeConnection(con);
        }
        deregisterDrivers();
    }

    private void closeConnection(final ProxyConnection con) {
        try {
            con.reallyClose();
        } catch (SQLException e) {
            LOGGER.error("SQLException in method destroyPool: {}",
                    e.getMessage());
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("SQLException in method deregisterDrivers: {}",
                        e.getMessage());
            }
        });
    }
}
