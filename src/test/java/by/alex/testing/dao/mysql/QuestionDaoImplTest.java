package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.QuestionDao;
import by.alex.testing.domain.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDaoImplTest {
    private QuestionDao questionDao;
    private Connection connection;

    @BeforeTest
    public void before() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/online-testing-test";
        String user = "root";
        String password = "12345678";

        connection = DriverManager.getConnection(url, user, password);
        questionDao = new QuestionDaoImpl(connection);
    }

    @Test
    public void testCreateSuccess() throws DaoException {
        Question expected = new Question("Title", "Body", QuestionType.YES_NO,
                new ArrayList<>(), 4, 1L);
        questionDao.create(expected);
        Assert.assertNotNull(expected.getId());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testReadAllThrowsUnsupported() throws DaoException {
        questionDao.readAll();
    }

    @Test
    public void testUpdateSuccess() throws DaoException {
        Question course = questionDao.readById(3L);
        course.setTitle("newTitle");
        questionDao.update(course);

        Assert.assertEquals(course.getTitle(), questionDao.readById(3L).getTitle());
    }

    @Test
    public void testReadByIdSuccess() throws DaoException {
        Assert.assertEquals(Long.valueOf(2L), questionDao.readById(2L).getId());
    }

    @Test
    public void testReadByTestIdSuccess() throws DaoException {
        Long testId = 1L;
        Assert.assertEquals(questionDao.readByTestId(testId).get(0).getTestId(), testId);
        Assert.assertEquals(questionDao.readByTestId(100L).size(), 0);
    }

    @Test
    public void testDeleteSuccess() throws DaoException {
        questionDao.delete(1L);

        Assert.assertNull(questionDao.readById(1L));
    }

    @AfterTest
    public void after() throws SQLException {
        connection.close();
        questionDao = null;
    }
}