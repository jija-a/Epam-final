package by.alex.testing.dao.mysql;


import by.alex.testing.dao.AbstractDao;
import by.alex.testing.dao.AnswerDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Answer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoImpl extends AbstractDao<Answer, Long> implements AnswerDao {

    private static final String SQL_SELECT_BY_QUESTION_ID =
            "SELECT `answer`.`id`, `answer`.`title`, `answer`.`is_right`, `answer`.`percent`, `answer`.`question_id` FROM `answer` WHERE `answer`.`question_id` = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `answer`.`id`, `answer`.`title`, `answer`.`is_right`, `answer`.`percent`, `answer`.`question_id` FROM `answer` WHERE `answer`.`id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO answer(title, is_right, percent, question_id) VALUES (?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `answer` SET `answer`.`title` = ?, `answer`.`is_right` = ?, `answer`.`percent` = ?, `answer`.`question_id` = ? WHERE `answer`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `answer` WHERE `answer`.`id` = ?";

    protected AnswerDaoImpl() {
    }

    @Override
    public List<Answer> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all answers is not supported");
    }

    @Override
    public List<Answer> readAllByQuestionId(long questionId) throws DaoException {
        List<Answer> answers = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_QUESTION_ID)) {
            ResultSet rs = ps.executeQuery();
            Answer answer = this.mapToEntity(rs);
            answers.add(answer);
        } catch (SQLException e) {
            throw new DaoException("Exception while reading answers by question id: ", e);
        }
        return answers;
    }

    @Override
    public Answer readById(Long id) throws DaoException {
        Answer answer = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                answer = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading answer by id: ", e);
        }
        return answer;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting answer: ", e);
        }
    }

    @Override
    public void create(Answer answer) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, answer);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    answer.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting answer: ", e);
        }
    }

    @Override
    public void update(Answer answer) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, answer);
            ps.setLong(5, answer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting answer: ", e);
        }
    }

    private Answer mapToEntity(ResultSet rs) throws SQLException {
        return Answer.builder()
                .id(rs.getLong("answer.id"))
                .title(rs.getString("answer.title"))
                .isRight(rs.getBoolean("answer.is_right"))
                .percent(rs.getDouble("answer.percent"))
                .questionId(rs.getLong("answer.question_id"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Answer answer) throws SQLException {
        ps.setString(1, answer.getTitle());
        ps.setBoolean(2, answer.getIsRight());
        ps.setDouble(3, answer.getPercent());
        ps.setLong(4, answer.getQuestionId());
    }
}
