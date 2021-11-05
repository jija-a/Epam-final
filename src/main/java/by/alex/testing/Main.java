package by.alex.testing;

import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.mysql.CourseDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;
import by.alex.testing.service.impl.TestServiceImpl;

public class Main {

    public static void main(String[] args) {
        long courseId = 1;
        CourseDao courseDao = new CourseDaoImpl(ConnectionPool.getInstance().getConnection());
        try {
            System.out.println(courseDao.readCourseByTitle("Физика 10 класс"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
