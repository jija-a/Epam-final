package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseDaoImplTest {
    private CourseDao courseDao;
    private Connection connection;

    @BeforeTest
    public void before() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/online-testing-test";
        String user = "root";
        String password = "12345678";

        connection = DriverManager.getConnection(url, user, password);
        courseDao = new CourseDaoImpl(connection);
    }

    @Test
    public void testCreateSuccess() throws DaoException {
        User teacher = new User("bboyse0", "Malorie", "Revie",
                "$s0$41010$8M89tDuPkHLWEzelh7zkHQ==$7KL+cjOHkWTL+g6Sp9G8DTUzYDm2c4YuxdBWqIHw8RA=".toCharArray(),
                UserRole.TEACHER);
        teacher.setId(2L);
        CourseCategory category = new CourseCategory("Computer Science");
        category.setId(1L);
        Course expected = new Course("Физика 10 класс", teacher, new HashMap<>(), new ArrayList<>(), category);

        courseDao.create(expected);
        Assert.assertNotNull(expected.getId());
    }

    @Test
    public void testReadAllSuccess() throws DaoException {
        Assert.assertEquals(courseDao.readAll().size(), 4);
    }

    @Test
    public void testUpdateSuccess() throws DaoException {
        Course course = courseDao.readById(3L);
        course.setName("newName");
        courseDao.update(course);

        Assert.assertEquals(course.getName(), courseDao.readById(3L).getName());
    }

    @Test
    public void testReadByIdSuccess() throws DaoException {
        Assert.assertEquals(Long.valueOf(2L), courseDao.readById(2L).getId());
    }

    @Test
    public void testReadByTitleSuccess() throws DaoException {
        String courseTitle = "10 класс Заборовский";
        Assert.assertEquals(courseDao.readCourseByTitle(courseTitle).get(0).getName(), courseTitle);
        Assert.assertEquals(courseDao.readCourseByTitle("SOME TITLE").size(), 0);
    }

    @Test
    public void testDeleteSuccess() throws DaoException {
        courseDao.delete(1L);

        Assert.assertNull(courseDao.readById(1L));
    }

    @AfterTest
    public void after() throws SQLException {
        connection.close();
        courseDao = null;
    }
}
