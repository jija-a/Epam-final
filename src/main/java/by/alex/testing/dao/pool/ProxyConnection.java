package by.alex.testing.dao.pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public final class ProxyConnection implements Connection {

    /**
     * @see Connection
     */
    private final Connection connection;

    /**
     * Class constructor.
     *
     * @param connection {@link Connection}
     */
    public ProxyConnection(final Connection connection) {
        this.connection = connection;
    }

    /**
     * @return {@link Connection}
     */
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
        return connection.isWrapperFor(arg0);
    }

    @Override
    public <T> T unwrap(final Class<T> arg0) throws SQLException {
        return connection.unwrap(arg0);
    }

    @Override
    public void abort(final Executor arg0) throws SQLException {
        connection.abort(arg0);
    }

    @Override
    public void clearWarnings() throws SQLException {
        connection.clearWarnings();
    }

    /**
     * Method puts {@link Connection} back to
     * {@link ConnectionPool}.
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        ConnectionPool.getInstance().releaseConnection(this);
    }

    /**
     * Method calls {@link Connection#close()}.
     *
     * @throws SQLException
     */
    public void reallyClose() throws SQLException {
        this.close();
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public Array createArrayOf(final String arg0, final Object[] arg1)
            throws SQLException {
        return connection.createArrayOf(arg0, arg1);
    }

    @Override
    public Blob createBlob() throws SQLException {
        return connection.createBlob();
    }

    @Override
    public Clob createClob() throws SQLException {
        return connection.createClob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return connection.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return connection.createSQLXML();
    }

    @Override
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    @Override
    public Statement createStatement(final int arg0, final int arg1)
            throws SQLException {

        return connection.createStatement(arg0, arg1);
    }

    @Override
    public Statement createStatement(final int arg0,
                                     final int arg1,
                                     final int arg2)
            throws SQLException {

        return connection.createStatement(arg0, arg1, arg2);
    }

    @Override
    public Struct createStruct(final String arg0, final Object[] arg1)
            throws SQLException {
        return connection.createStruct(arg0, arg1);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    @Override
    public String getCatalog() throws SQLException {
        return connection.getCatalog();
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return connection.getClientInfo();
    }

    @Override
    public String getClientInfo(final String arg0) throws SQLException {
        return connection.getClientInfo(arg0);
    }

    @Override
    public int getHoldability() throws SQLException {
        return connection.getHoldability();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return connection.getNetworkTimeout();
    }

    @Override
    public String getSchema() throws SQLException {
        return connection.getSchema();
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return connection.getTransactionIsolation();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return connection.getTypeMap();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return connection.getWarnings();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return connection.isReadOnly();
    }

    @Override
    public boolean isValid(final int arg0) throws SQLException {
        return connection.isValid(arg0);
    }

    @Override
    public String nativeSQL(final String arg0) throws SQLException {
        return connection.nativeSQL(arg0);
    }

    @Override
    public CallableStatement prepareCall(final String arg0)
            throws SQLException {

        return connection.prepareCall(arg0);
    }

    @Override
    public CallableStatement prepareCall(final String arg0,
                                         final int arg1,
                                         final int arg2)
            throws SQLException {

        return connection.prepareCall(arg0, arg1, arg2);
    }

    @Override
    public CallableStatement prepareCall(final String arg0,
                                         final int arg1,
                                         final int arg2,
                                         final int arg3)
            throws SQLException {

        return connection.prepareCall(arg0, arg1, arg2, arg3);
    }

    @Override
    public PreparedStatement prepareStatement(final String arg0)
            throws SQLException {

        return connection.prepareStatement(arg0);
    }

    @Override
    public PreparedStatement prepareStatement(final String arg0,
                                              final int arg1)
            throws SQLException {

        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(final String arg0,
                                              final int[] arg1)
            throws SQLException {

        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(final String arg0,
                                              final String[] arg1)
            throws SQLException {

        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(final String arg0,
                                              final int arg1,
                                              final int arg2)
            throws SQLException {

        return connection.prepareStatement(arg0, arg1, arg2);
    }

    @Override
    public PreparedStatement prepareStatement(final String arg0,
                                              final int arg1,
                                              final int arg2,
                                              final int arg3)
            throws SQLException {

        return connection.prepareStatement(arg0, arg1, arg2, arg3);
    }

    @Override
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
        connection.releaseSavepoint(arg0);
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void rollback(final Savepoint arg0) throws SQLException {
        connection.rollback(arg0);
    }

    @Override
    public void setAutoCommit(final boolean arg0) throws SQLException {
        connection.setAutoCommit(arg0);
    }

    @Override
    public void setCatalog(final String arg0) throws SQLException {
        connection.setCatalog(arg0);
    }

    @Override
    public void setClientInfo(final Properties arg0)
            throws SQLClientInfoException {

        connection.setClientInfo(arg0);
    }

    @Override
    public void setClientInfo(final String arg0, final String arg1)
            throws SQLClientInfoException {

        connection.setClientInfo(arg0, arg1);
    }

    @Override
    public void setHoldability(final int arg0) throws SQLException {
        connection.setHoldability(arg0);
    }

    @Override
    public void setNetworkTimeout(final Executor arg0, final int arg1)
            throws SQLException {

        connection.setNetworkTimeout(arg0, arg1);
    }

    @Override
    public void setReadOnly(final boolean arg0) throws SQLException {
        connection.setReadOnly(arg0);
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return connection.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(final String arg0) throws SQLException {
        return connection.setSavepoint(arg0);
    }

    @Override
    public void setSchema(final String arg0) throws SQLException {
        connection.setSchema(arg0);
    }

    @Override
    public void setTransactionIsolation(final int arg0) throws SQLException {
        connection.setTransactionIsolation(arg0);
    }

    @Override
    public void setTypeMap(final Map<String, Class<?>> arg0)
            throws SQLException {

        connection.setTypeMap(arg0);
    }
}
