package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.LessonDao;
import by.alex.testing.domain.Lesson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LessonDaoImpl extends AbstractMySqlDao implements LessonDao {

    /**
     * Query to select all {@link Lesson}'s.
     */
    private static final String SQL_SELECT_ALL =
            "SELECT `lesson`.`id`, `lesson`.`title`, `lesson`.`course_id`, `lesson`.`start_date`, `lesson`.`end_date` FROM `lesson`";

    /**
     * Query to select all {@link Lesson}'s by
     * {@link by.alex.testing.domain.Course} id limited.
     */
    private static final String SQL_SELECT_ALL_BY_COURSE_ID_WITH_LIMIT =
            "SELECT `lesson`.`id`, `lesson`.`title`, `lesson`.`course_id`, `lesson`.`start_date`, `lesson`.`end_date` FROM `lesson` WHERE `lesson`.`course_id` = ? LIMIT ?, ?;";

    /**
     * Query to select {@link Lesson} by id.
     */
    private static final String SQL_SELECT_BY_ID =
            "SELECT `lesson`.`id`, `lesson`.`title`, `lesson`.`course_id`, `lesson`.`start_date`, `lesson`.`end_date` FROM `lesson` WHERE `lesson`.`id` = ?";

    /**
     * Query to select all {@link Lesson}'s by
     * {@link by.alex.testing.domain.Course} and
     * {@link by.alex.testing.domain.User} id limited.
     */
    private static final String SQL_SELECT_BY_COURSE_STUDENT_ID_LIMIT =
            "SELECT `lesson`.`id`, `lesson`.`title`, `lesson`.`course_id`, `lesson`.`start_date`, `lesson`.`end_date` FROM `lesson` JOIN `attendance` `a` on `lesson`.`id` = `a`.`lesson_id` WHERE `lesson`.`course_id` = ? AND `a`.`user_id` = ? LIMIT ?, ?;";

    /**
     * Query to create {@link Lesson}.
     */
    private static final String SQL_CREATE =
            "INSERT INTO `lesson` (`title`, `course_id`, `start_date`, `end_date`) VALUES (?, ?, ?, ?)";

    /**
     * Query to update {@link Lesson}.
     */
    private static final String SQL_UPDATE =
            "UPDATE `lesson` SET `lesson`.`title` = ?, `lesson`.`course_id` = ?, `lesson`.`start_date` = ?, `lesson`.`end_date` = ? WHERE `lesson`.`id` = ?;";

    /**
     * Query to delete {@link Lesson}.
     */
    private static final String SQL_DELETE =
            "DELETE FROM `lesson` WHERE `lesson`.`id` = ?;";

    /**
     * Query to count all {@link Lesson}'s.
     */
    private static final String SQL_COUNT =
            "SELECT COUNT(*) FROM `lesson` WHERE `lesson`.`course_id` = ?";

    /**
     * Query to count all student {@link Lesson}.
     */
    private static final String SQL_COUNT_BY_STUDENT =
            "SELECT COUNT(*) FROM `lesson` JOIN `attendance` `a` on `lesson`.`id` = `a`.`lesson_id` WHERE `lesson`.`course_id` = ? AND `a`.`user_id` = ?";

    @Override
    public boolean save(final Lesson entity) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {

            this.mapFromEntity(ps, entity);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating lesson: ", e);
        }
        return false;
    }

    @Override
    public List<Lesson> findAll() throws DaoException {
        List<Lesson> lessons = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = this.mapToEntity(rs);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all lessons: ", e);
        }
        return lessons;
    }

    @Override
    public Lesson findOne(final long id) throws DaoException {
        Lesson lesson = null;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lesson = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading lesson by id: ", e);
        }
        return lesson;
    }

    @Override
    public List<Lesson> findByCourseId(final long courseId,
                                       final int start,
                                       final int recordsPerPage)
            throws DaoException {
        List<Lesson> lessons = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL_BY_COURSE_ID_WITH_LIMIT)) {
            ps.setLong(1, courseId);
            ps.setInt(2, start);
            ps.setInt(3, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = this.mapToEntity(rs);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " all lessons by course id: ", e);
        }
        return lessons;
    }

    @Override
    public List<Lesson> findByCourseAndStudentId(final long courseId,
                                                 final long studentId,
                                                 final int start,
                                                 final int recordsPerPage)
            throws DaoException {

        List<Lesson> lessons = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_COURSE_STUDENT_ID_LIMIT)) {
            ps.setLong(1, courseId);
            ps.setLong(2, studentId);
            ps.setInt(3, start);
            ps.setInt(4, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = this.mapToEntity(rs);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " all lessons by course and student id: ", e);
        }
        return lessons;
    }

    @Override
    public boolean update(final Lesson entity) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, entity);
            ps.setLong(5, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating lesson: ", e);
        }
    }

    @Override
    public boolean delete(final long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting lesson: ", e);
        }
    }

    @Override
    public int count(final long courseId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while count"
                    + " all users by search request: ", e);
        }
        return count;
    }

    @Override
    public int count(final long courseId, final long studentId)
            throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_BY_STUDENT)) {
            ps.setLong(1, courseId);
            ps.setLong(2, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while count all"
                    + " users by search request: ", e);
        }
        return count;
    }

    private Lesson mapToEntity(final ResultSet rs) throws SQLException {
        return Lesson.builder()
                .id(rs.getLong("lesson.id"))
                .title(rs.getString("lesson.title"))
                .courseId(rs.getLong("lesson.course_id"))
                .startDate(rs.getTimestamp("lesson.start_date")
                        .toLocalDateTime())
                .endDate(rs.getTimestamp("lesson.end_date").toLocalDateTime())
                .build();
    }

    private void mapFromEntity(final PreparedStatement ps, final Lesson lesson)
            throws SQLException {
        ps.setString(1, lesson.getTitle());
        ps.setLong(2, lesson.getCourseId());
        ps.setTimestamp(3, Timestamp.valueOf(lesson.getStartDate()));
        ps.setTimestamp(4, Timestamp.valueOf(lesson.getEndDate()));
    }
}
