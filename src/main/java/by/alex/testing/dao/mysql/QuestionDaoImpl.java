package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.QuestionDao;
import by.alex.testing.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private static final String SQL_SELECT_BY_TEST_ID =
            "SELECT `question`.`title`, `question`.`body`, `question`.`question_type_id` FROM `question` JOIN `test_question` tq on `question`.`id` = `tq`.`question_id` WHERE `tq`.`test_id` = ?;";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `question`.`title`, `question`.`body`, `question`.`question_type_id` FROM `question` WHERE `question`.`id` = ?;";

    private static final String SQL_CREATE =
            "INSERT INTO `question`(`title`, `body`, `question_type_id`)\n" +
                    "VALUES (?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `question`\n" +
                    "SET `question`.`title` = ?\n" +
                    "    AND `question`.`body` = ?\n" +
                    "    AND `question`.`question_type_id` = ?\n" +
                    "WHERE `question`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE\n" +
                    "FROM `question`\n" +
                    "WHERE `question`.`id` = ?;";

    private Connection connection;

    @Override
    public List<Question> readAll() throws DaoException {
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TEST_ID)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question question = this.mapToEntity(rs);
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all questions: ", e);
        }
        return questions;
    }

    @Override
    public Question readById(Long id) throws DaoException {
        Question question = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                question = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading question by id: ", e);
        }
        return question;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting question: ", e);
        }
    }

    @Override
    public void create(Question question) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE)) {
            this.mapFromEntity(ps, question);
        } catch (SQLException e) {
            throw new DaoException("Exception while creating question: ", e);
        }
    }

    @Override
    public void update(Question question) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, question);
            ps.setLong(4, question.getId());
        } catch (SQLException e) {
            throw new DaoException("Exception while updating question: ", e);
        }
    }

    private Question mapToEntity(ResultSet rs) throws SQLException {
        return Question.builder()
                .id(rs.getLong("question.id"))
                .title(rs.getString("question.title"))
                .body(rs.getString("question.body"))
                .type(QuestionType.resolveTypeById(rs.getInt("question.question_type_id")))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Question question) throws SQLException {
        ps.setString(1, question.getTitle());
        ps.setString(2, question.getBody());
        ps.setInt(3, question.getType().getId());
    }
}
