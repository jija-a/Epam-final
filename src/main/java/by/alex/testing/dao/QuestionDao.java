package by.alex.testing.dao;

import by.alex.testing.domain.Question;

import java.util.List;

public interface QuestionDao extends Dao<Question, Long> {

    List<Question> readByTestId(long testId) throws DaoException;
}
