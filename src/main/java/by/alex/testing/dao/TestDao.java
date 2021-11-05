package by.alex.testing.dao;

import by.alex.testing.domain.Quiz;

import java.util.List;

public interface TestDao extends Dao<Quiz, Long> {

    List<Quiz> readAllTestsByCourseId(long id) throws DaoException;
}
