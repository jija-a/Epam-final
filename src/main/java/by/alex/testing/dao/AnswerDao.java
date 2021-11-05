package by.alex.testing.dao;

import by.alex.testing.domain.Answer;

import java.util.List;

public interface AnswerDao extends Dao<Answer, Long> {

    List<Answer> readAllByQuestionId(long questionId) throws DaoException;
}
