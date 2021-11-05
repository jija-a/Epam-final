package by.alex.testing.dao.pool;

import by.alex.testing.dao.InitializingError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> connections;
    private static AtomicBoolean created = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock(true);
    private DatabaseConfig config;

    private ConnectionPool() {
        this.init();
    }

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
            throw new InitializingError("Initializing connection pool failed", e);
        }
        initConnectionPool();
    }

    private void initConnectionPool() throws InitializingError {
        this.config = DatabaseConfig.getInstance();
        this.connections = new ArrayBlockingQueue<>(config.getPoolSize());
        for (int i = 0; i < config.getPoolSize(); i++) {
            try {
                connections.add(this.createConnection());
            } catch (SQLException e) {
                throw new InitializingError("Database connection failed: ", e);
            }
        }
    }

    private ProxyConnection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                config.getUrl() + config.getDbName(), config.getUser(), config.getPassword());
        LOGGER.trace("Connection created");
        return new ProxyConnection(connection);
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.error("Connection interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            ProxyConnection con = (ProxyConnection) connection;
            connections.add(con);
        }
        LOGGER.warn("Can't return connection, possible connection leaking");
    }

    public void destroyPool() {
        for (Connection con : this.connections) {
            this.closeConnection(con);
        }
        deregisterDrivers();
    }

    private void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException in method destroyPool: {}", e.getMessage());
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("SQLException in method deregisterDrivers: {}", e.getMessage());
            }
        });
    }
}
