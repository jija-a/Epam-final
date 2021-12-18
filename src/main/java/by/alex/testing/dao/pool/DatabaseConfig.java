package by.alex.testing.dao.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class DatabaseConfig {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(DatabaseConfig.class);

    /**
     * {@link DatabaseConfig} instance. Singleton pattern.
     */
    private static final DatabaseConfig CONFIG = new DatabaseConfig();

    /**
     * {@link String} DB url.
     */
    private static final String URL;

    /**
     * {@link String} DB name.
     */
    private static final String DB_NAME;

    /**
     * {@link String} DB user name.
     */
    private static final String USER;

    /**
     * {@link String} DB user password.
     */
    private static final String PASSWORD;

    /**
     * {@link ConnectionPool} size.
     */
    private static final int POOL_SIZE;

    /**
     * @return {@link DatabaseConfig}
     */
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
            throw new InitializingError("Error while initializing"
                    + " database configuration", e);
        }
    }

    /**
     * @return {@link String} DB url
     */
    public String getUrl() {
        return URL;
    }

    /**
     * @return {@link String} DB name
     */
    public String getDbName() {
        return DB_NAME;
    }

    /**
     * @return {@link String} DB user name
     */
    public String getUser() {
        return USER;
    }

    /**
     * @return {@link String} DB user password
     */
    public String getPassword() {
        return PASSWORD;
    }

    /**
     * @return {@link Integer} DB pool size
     */
    public int getPoolSize() {
        return POOL_SIZE;
    }
}
