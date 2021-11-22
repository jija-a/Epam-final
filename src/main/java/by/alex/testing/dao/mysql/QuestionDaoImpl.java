package by.alex.testing.dao.mysql;

import by.alex.testing.dao.AbstractDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.QuestionDao;
import by.alex.testing.domain.Question;
import by.alex.testing.domain.QuestionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl extends AbstractDao<Question, Long> implements QuestionDao {

    private static final String SQL_SELECT_BY_TEST_ID =
            "SELECT `question`.`id`, `question`.`question_type_id`, `question`.`test_id`, `question`.`title`, `question`.`body`, `question`.`points` FROM `question` WHERE `question`.`test_id` = ?;";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `question`.`id`, `question`.`question_type_id`, `question`.`test_id`, `question`.`title`, `question`.`body`, `question`.`points` FROM `question` WHERE `question`.`id` = ?;";

    private static final String SQL_CREATE =
            "INSERT INTO `question`(`title`, `body`, `question_type_id`, `test_id`, `points`) VALUES (?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `question` SET `question`.`title` = ?, `question`.`body` = ?, `question`.question_type_id = ?, `question`.test_id = ?, `question`.`points` = ? WHERE `question`.`id` = ?";

    private static final String SQL_DELETE =
            "DELETE FROM `question` WHERE `question`.`id` = ?;";

    protected QuestionDaoImpl() {
    }

    @Override
    public List<Question> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all questions is unsupported");
    }

    @Override
    public List<Question> readByTestId(long testId) throws DaoException {
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TEST_ID)) {
            ps.setLong(1, testId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question question = this.mapToEntity(rs);
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading question by id: ", e);
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
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, question);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    question.setId(rs.getLong(1));
                }
            }
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
                .type(QuestionType.resolveTypeById(rs.getInt("question.question_type_id")))
                .testId(rs.getLong("question.test_id"))
                .title(rs.getString("question.title"))
                .body(rs.getString("question.body"))
                .points(rs.getInt("question.points"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Question question) throws SQLException {
        ps.setString(1, question.getTitle());
        ps.setString(2, question.getBody());
        ps.setInt(3, question.getType().getId());
        ps.setLong(4, question.getTestId());
        ps.setInt(5, question.getPoints());
    }
}
