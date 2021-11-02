package by.alex.testing.dao.mysql;


import by.alex.testing.dao.AnswerDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoImpl implements AnswerDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `answer`.`id`, `answer`.`title`, `answer`.`is_right`, `answer`.`question_id`\n" +
                    "FROM `answer`";

    private static final String SQL_SELECT_BY_ID =
            SQL_SELECT_ALL + "\nWHERE `answer`.`id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO answer(title, is_right, question_id)\n" +
                    "VALUES (?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `answer`\n" +
                    "SET `answer`.`title` = ?\n" +
                    "    AND `answer`.`is_right` = ?\n" +
                    "    AND `answer`.`question_id` = ?\n" +
                    "WHERE `answer`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE\n" +
                    "FROM `answer`\n" +
                    "WHERE `answer`.`id` = ?";

    private Connection connection;

    public AnswerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Answer> readAll() throws DaoException {
        List<Answer> answers = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            Answer answer = this.mapToEntity(rs);
            answers.add(answer);
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all answers: ", e);
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
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE)) {
            this.mapFromEntity(ps, answer);
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting answer: ", e);
        }
    }

    @Override
    public void update(Answer answer) throws DaoException {

    }

    private Answer mapToEntity(ResultSet rs) throws SQLException {
        return Answer.builder()
                .id(rs.getLong("answer.id"))
                .title(rs.getString("answer.title"))
                .isRight(rs.getBoolean("answer.is_right"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Answer answer) throws SQLException {
        ps.setString(1, answer.getTitle());
        ps.setBoolean(2, answer.getIsRight());
    }
}
