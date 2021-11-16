package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.domain.Quiz;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TestDaoImplTest {
    private TestDao testDao;
    private Connection connection;

    @BeforeTest
    public void before() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/online-testing-test";
        String user = "root";
        String password = "12345678";

        connection = DriverManager.getConnection(url, user, password);
        testDao = new TestDaoImpl(connection);
    }

    @Test
    public void testCreateSuccess() throws DaoException, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Quiz expected = null;
        testDao.create(expected);
        Assert.assertNotNull(expected.getId());
    }

    @Test
    public void testUpdateSuccess() throws DaoException {
        Quiz test = testDao.readById(2L);
        test.setTitle("newTitle");
        testDao.update(test);

        Assert.assertEquals(test.getTitle(), testDao.readById(2L).getTitle());
    }

    @Test
    public void testReadByIdSuccess() throws DaoException {
        Assert.assertEquals(Long.valueOf(2L), testDao.readById(2L).getId());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testReadAllIsUnsupported() throws DaoException {
        testDao.readAll();
    }

    @Test
    public void testReadByCourseIdSuccess() throws DaoException {
        Long courseId = 4L;
        Assert.assertEquals(testDao.readAllTestsByCourseId(courseId).get(0).getCourseId(), courseId);
        Assert.assertEquals(testDao.readAllTestsByCourseId(100L).size(), 0);
    }

    @Test
    public void testDeleteSuccess() throws DaoException {
        testDao.delete(1L);

        Assert.assertNull(testDao.readById(1L));
    }

    @AfterTest
    public void after() throws SQLException {
        connection.close();
        testDao = null;
    }
}