package by.alex.testing.dao;

import by.alex.testing.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> readByTestId(long testId) throws DaoException;
}
