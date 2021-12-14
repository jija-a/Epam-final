package by.alex.testing.dao.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractMySqlDao {

    private static final Logger logger =
            LoggerFactory.getLogger(AbstractMySqlDao.class);

    protected Connection connection;

    public void setConnection(Connection connection) {
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

    protected String createLikeParameter(String param) {
        return "%" + param + "%";
    }
}
