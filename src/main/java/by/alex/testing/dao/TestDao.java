package by.alex.testing.dao;

import by.alex.testing.domain.Quiz;

import java.util.List;

public interface TestDao {

    List<Quiz> readAllTestsByCourseId(long courseId, int start, int recOnPage) throws DaoException;

    List<Quiz> readAllTestsByUserIdSortedByDate(long userId, int limit) throws DaoException;

    Integer count(long courseId) throws DaoException;
}
