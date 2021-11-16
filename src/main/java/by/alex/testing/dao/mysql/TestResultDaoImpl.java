package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestResultDao;
import by.alex.testing.domain.Quiz;
import by.alex.testing.domain.TestResult;
import by.alex.testing.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestResultDaoImpl implements TestResultDao {

    private static final String SQL_SELECT_BY_TEST_ID =
            "SELECT `test_result`.`id`, `test_result`.`test_id`, `test_result`.`user_id`, `test_result`.`percent`, `test_result`.`test_started`, `test_result`.`test_ended` FROM `test_result` WHERE `test_result`.`test_id` = ?;";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `test_result`.`id`, `test_result`.`test_id`, `test_result`.`user_id`, `test_result`.`percent`, `test_result`.`test_started`, `test_result`.`test_ended` FROM `test_result` WHERE `test_result`.`id` = ?";

    private static final String SQL_SELECT_BY_USER_ID =
            "SELECT `test_result`.`id`, `test_result`.`test_id`, `test_result`.`user_id`, `test_result`.`percent`, `test_result`.`test_started`, `test_result`.`test_ended` FROM `test_result` WHERE `test_result`.`user_id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO `test_result`(test_id, user_id, percent, test_started, test_ended) VALUES (?, ?, ?, ?, ?);";

    private static final String SQL_DELETE =
            "DELETE FROM `test_result` WHERE `test_result`.id = ?";

    private final Connection connection;

    public TestResultDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<TestResult> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all test results is unsupported");
    }

    @Override
    public List<TestResult> readAllByTestId(long testId) throws DaoException {
        List<TestResult> testResults = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TEST_ID)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestResult testResult = this.mapToEntity(rs);
                testResults.add(testResult);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading test results by test id: ", e);
        }
        return testResults;
    }

    @Override
    public List<TestResult> readAllByUserId(long userId) throws DaoException {
        List<TestResult> testResults = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_USER_ID)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestResult testResult = this.mapToEntity(rs);
                testResults.add(testResult);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading test results by test id: ", e);
        }
        return testResults;
    }

    @Override
    public TestResult readById(Long id) throws DaoException {
        TestResult testResult = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                testResult = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading test result by id: ", e);
        }
        return testResult;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting test result: ", e);
        }
    }

    @Override
    public void create(TestResult testResult) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE)) {
            this.mapFromEntity(ps, testResult);
        } catch (SQLException e) {
            throw new DaoException("Exception while creating test result: ", e);
        }
    }

    @Override
    public void update(TestResult testResult) throws DaoException {
        throw new UnsupportedOperationException("Updating test result is not supported");
    }

    private TestResult mapToEntity(ResultSet rs) throws SQLException {
        return TestResult.builder()
                .id(rs.getLong("test_result.id"))
                .test(Quiz.builder()
                        .id(rs.getLong("test_result.test_id"))
                        .build())
                .user(User.builder()
                        .id(rs.getLong("test_result.user_id"))
                        .build())
                .percent(rs.getDouble("test_result.percent"))
                .testStarted(rs.getTimestamp("test_result.test_started").toLocalDateTime())
                .testEnded(rs.getTimestamp("test_result.test_ended").toLocalDateTime())
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, TestResult result) throws SQLException {
        ps.setLong(1, result.getTest().getId());
        ps.setLong(2, result.getUser().getId());
        ps.setDouble(3, result.getPercent());
        ps.setObject(4, result.getTestStarted());
        ps.setObject(5, result.getTestEnded());
    }
}
