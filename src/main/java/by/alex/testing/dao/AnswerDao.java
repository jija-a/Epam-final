package by.alex.testing.dao;

import by.alex.testing.domain.Answer;

import java.util.List;

public interface AnswerDao {

    List<Answer> readAllByQuestionId(long questionId) throws DaoException;
}
