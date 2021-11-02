package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.TestDao;
import by.alex.testing.domain.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {

    private static final String SQL_SELECT_ALL_BY_COURSE_ID =
            "SELECT `test`.`id`, `test`.`title`, `test`.`course_id`, `test`.attempts, `test`.start_date, `test`.`end_date`, `test`.`time_to_answer` FROM `test` WHERE `course_id` = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `test`.`id`, `test`.`title`, `test`.`course_id`, `test`.attempts, `test`.start_date, `test`.`end_date`, `test`.`time_to_answer` FROM `test` WHERE `test`.`id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO `test`(`title`, `course_id`, `attempts`, `start_date`, `end_date`, `time_to_answer`) VALUES (?, ?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `test` SET `test`.`title` = ?, `test`.`course_id` = ?, `test`.`attempts` = ?, `test`.`start_date` = ?, `test`.`end_date` = ?, `test`.time_to_answer = ? WHERE `test`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `test` WHERE `test`.`id` = ?;";

    private final Connection connection;

    public TestDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Test> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all tests is unsupported");
    }

    @Override
    public List<Test> readAllTestsByCourseId(long courseId) throws DaoException {
        List<Test> tests = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_BY_COURSE_ID)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Test test = this.mapToEntity(rs);
                tests.add(test);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all tests by course id: ", e);
        }
        return tests;
    }

    @Override
    public Test readById(Long id) throws DaoException {
        Test test = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                test = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading test by id: ", e);
        }
        return test;
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
    public void create(Test test) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, test);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    test.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating test: ", e);
        }
    }

    @Override
    public void update(Test test) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, test);
            ps.setLong(7, test.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating test: ", e);
        }
    }

    private Test mapToEntity(ResultSet rs) throws SQLException {
        return Test.builder()
                .id(rs.getLong("test.id"))
                .title(rs.getString("test.title"))
                .attempts(rs.getInt("test.attempts"))
                .startDate(rs.getDate("test.start_date"))
                .endDate(rs.getDate("test.end_date"))
                .timeToAnswer(rs.getInt("test.time_to_answer"))
                .courseId(rs.getLong("test.course_id"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Test test) throws SQLException {
        ps.setString(1, test.getTitle());
        ps.setLong(2, test.getCourseId());
        ps.setInt(3, test.getAttempts());
        ps.setObject(4, test.getStartDate());
        ps.setObject(5, test.getEndDate());
        ps.setInt(6, test.getTimeToAnswer());
    }
}
