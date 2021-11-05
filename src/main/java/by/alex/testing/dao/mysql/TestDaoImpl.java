package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.domain.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {

    private static final String SQL_SELECT_ALL_BY_COURSE_ID =
            "SELECT `test`.`id`, `test`.`title`, `test`.`course_id`, `test`.attempts, `test`.start_date, `test`.`end_date`, `test`.`time_to_answer`, `test`.`max_score` FROM `test` WHERE `course_id` = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `test`.`id`, `test`.`title`, `test`.`course_id`, `test`.attempts, `test`.start_date, `test`.`end_date`, `test`.`time_to_answer`, `test`.`max_score` FROM `test` WHERE `test`.`id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO `test`(`title`, `course_id`, `attempts`, `start_date`, `end_date`, `time_to_answer`, `max_score`) VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `test` SET `test`.`title` = ?, `test`.`course_id` = ?, `test`.`attempts` = ?, `test`.`start_date` = ?, `test`.`end_date` = ?, `test`.time_to_answer = ?, `test`.`max_score` = ? WHERE `test`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `test` WHERE `test`.`id` = ?;";

    private final Connection connection;

    public TestDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Quiz> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all tests is unsupported");
    }

    @Override
    public List<Quiz> readAllTestsByCourseId(long courseId) throws DaoException {
        List<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_BY_COURSE_ID)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = this.mapToEntity(rs);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all tests by course id: ", e);
        }
        return quizzes;
    }

    @Override
    public Quiz readById(Long id) throws DaoException {
        Quiz quiz = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                quiz = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading test by id: ", e);
        }
        return quiz;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting test: ", e);
        }
    }

    @Override
    public void create(Quiz quiz) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, quiz);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    quiz.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating test: ", e);
        }
    }

    @Override
    public void update(Quiz quiz) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, quiz);
            ps.setLong(8, quiz.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating test: ", e);
        }
    }

    private Quiz mapToEntity(ResultSet rs) throws SQLException {
        return Quiz.builder()
                .id(rs.getLong("test.id"))
                .title(rs.getString("test.title"))
                .courseId(rs.getLong("test.course_id"))
                .attempts(rs.getInt("test.attempts"))
                .startDate(rs.getDate("test.start_date"))
                .endDate(rs.getDate("test.end_date"))
                .timeToAnswer(rs.getInt("test.time_to_answer"))
                .maxScore(rs.getInt("test.max_score"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Quiz quiz) throws SQLException {
        ps.setString(1, quiz.getTitle());
        ps.setLong(2, quiz.getCourseId());
        ps.setInt(3, quiz.getAttempts());
        ps.setObject(4, quiz.getStartDate());
        ps.setObject(5, quiz.getEndDate());
        ps.setInt(6, quiz.getTimeToAnswer());
        ps.setInt(7, quiz.getMaxScore());
    }
}
