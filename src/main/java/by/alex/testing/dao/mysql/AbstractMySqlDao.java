package by.alex.testing.dao.mysql;

import java.sql.Connection;

public abstract class AbstractMySqlDao {

    /**
     * Connection that {@link by.alex.testing.dao.BaseDao} implementations
     * use.
     *
     * @see Connection
     */
    protected Connection connection;

    /**
     * Method which {@link by.alex.testing.dao.TransactionHandler} uses
     * to set {@link by.alex.testing.dao.pool.ProxyConnection}
     * to DAO implementations.
     *
     * @param connection {@link Connection}
     */
    public void setConnection(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Method allows creating LIKE parameters for MySQL statements.
     *
     * @param param {@link String} parameter
     * @return {@link String} LIKE parameter
     */
    protected String createLikeParameter(final String param) {
        return "%" + param + "%";
    }
}
