package by.alex.testing.dao.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatabaseConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final DatabaseConfig CONFIG = new DatabaseConfig();

    private static final String URL;
    private static final String DB_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final int POOL_SIZE;

    public static DatabaseConfig getInstance() {
        return CONFIG;
    }

    private DatabaseConfig() {
    }

    static {
        LOGGER.trace("Initializing database configuration");
        try {
            ResourceBundle rb = ResourceBundle.getBundle("db");
            URL = rb.getString("db.url");
            DB_NAME = rb.getString("db.name");
            USER = rb.getString("db.user");
            PASSWORD = rb.getString("db.password");
            POOL_SIZE = Integer.parseInt(rb.getString("db.pool_size"));
        } catch (ExceptionInInitializerError | MissingResourceException e) {
            throw new InitializingException("Error while initializing database configuration", e);
        }
    }

    public String getUrl() {
        return URL;
    }

    public String getDbName() {
        return DB_NAME;
    }

    public String getUser() {
        return USER;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public int getPoolSize() {
        return POOL_SIZE;
    }
}
