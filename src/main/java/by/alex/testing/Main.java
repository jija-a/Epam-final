package by.alex.testing;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.dao.mysql.TestDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;

public class Main {

    public static void main(String[] args) throws DaoException {
        TestDao dao = new TestDaoImpl(ConnectionPool.getInstance().getConnection());
        System.out.println(dao.readAllTestsByUserIdSortedByDate(2L, 10));
    }
}
