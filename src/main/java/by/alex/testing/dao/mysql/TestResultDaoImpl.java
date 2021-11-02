package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestResultDao;
import by.alex.testing.domain.Test;
import by.alex.testing.domain.TestResult;
import by.alex.testing.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestResultDaoImpl implements TestResultDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `test_result`.id, `test_result`.`test_id`, `test_result`.`user_id`, `test_result`.`score`, `test_result`.`date`\n" +
                    "FROM `test_result`";

    private static final String SQL_SELECT_BY_ID =
            SQL_SELECT_ALL + "\n WHERE `test_result`.`id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO test_result(test_id, user_id, score, date)\n" +
                    "VALUES (?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `test_result`\n" +
                    "SET `test_result`.`test_id` = ?\n" +
                    "    AND `test_result`.`user_id` = ?\n" +
                    "    AND `test_result`.`score` = ?\n" +
                    "    AND `test_result`.`date` = ?\n" +
                    "WHERE `test_result`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE\n" +
                    "FROM `test_result`\n" +
                    "WHERE `test_result`.`id` = ?;";

    private Connection connection;

    @Override
    public List<TestResult> readAll() throws DaoException {
        List<TestResult> testResults = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestResult testResult = this.mapToEntity(rs);
                testResults.add(testResult);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all test results: ", e);
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
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, testResult);
            ps.setLong(5, testResult.getId());
        } catch (SQLException e) {
            throw new DaoException("Exception while updating test: ", e);
        }
    }

    private TestResult mapToEntity(ResultSet rs) throws SQLException {
        return TestResult.builder()
                .id(rs.getLong("test_result.id"))
                .test(Test.builder()
                        .id(rs.getLong("test_result.test_id"))
                        .build())
                .user(User.builder()
                        .id(rs.getLong("test_result.user_id"))
                        .build())
                .score(rs.getDouble("test_result.score"))
                .date(rs.getDate("test_result.date"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, TestResult result) throws SQLException {
        ps.setLong(1, result.getTest().getId());
        ps.setLong(2, result.getUser().getId());
        ps.setDouble(3, result.getScore());
        ps.setDate(4, (Date) result.getDate());
    }
}
