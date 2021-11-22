package by.alex.testing.dao.pool;

import by.alex.testing.dao.InitializingError;
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

public class ConnectionPool {

    private static final Logger logger =
            LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> availableConnections;
    private Queue<ProxyConnection> takenConnections;
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
        logger.trace("Initializing connection pool");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new InitializingError("Initializing connection pool failed", e);
        }
        initConnectionPool();
    }

    private void initConnectionPool() throws InitializingError {
        this.config = DatabaseConfig.getInstance();
        this.availableConnections = new ArrayBlockingQueue<>(config.getPoolSize());
        this.takenConnections = new ArrayDeque<>();
        for (int i = 0; i < config.getPoolSize(); i++) {
            try {
                availableConnections.add(this.createConnection());
                logger.info("Connection {} added", i + 1);
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
                    if (!availableConnections.offer(new ProxyConnection(connection))){
                        break;
                    }
                } catch (SQLException e) {
                    logger.error("Error while creating connections", e);
                }
            }
            logger.error("{} Connections have been lost and created again", count);
        }
    }

    private ProxyConnection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                config.getUrl() + config.getDbName(), config.getUser(), config.getPassword());
        logger.info("Connection created");
        return new ProxyConnection(connection);
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            takenConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Connection interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            ProxyConnection con = (ProxyConnection) connection;
            takenConnections.remove(con);
            availableConnections.add(con);
        } else {
            logger.warn("Can't return connection, possible connection leaking");
        }
    }

    public void destroyPool() {
        for (ProxyConnection con : this.availableConnections) {
            this.closeConnection(con);
        }
        deregisterDrivers();
    }

    private void closeConnection(ProxyConnection con) {
        try {
            con.reallyClose();
        } catch (SQLException e) {
            logger.error("SQLException in method destroyPool: {}", e.getMessage());
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("SQLException in method deregisterDrivers: {}", e.getMessage());
            }
        });
    }
}
