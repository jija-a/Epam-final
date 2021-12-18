package by.alex.testing.dao.mysql;

import by.alex.testing.dao.AttendanceDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public final class AttendanceDaoImpl extends AbstractMySqlDao
        implements AttendanceDao {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AttendanceDaoImpl.class);

    /**
     * MySQL query to create {@link Attendance}.
     */
    private static final String SQL_CREATE =
            "INSERT INTO `attendance` (`lesson_id`, `user_id`, `mark`, `attended`) VALUES (?, ?, ?, ?);";

    /**
     * MySQL select query
     * {@link Attendance} by {@link by.alex.testing.domain.Lesson} id.
     */
    private static final String SQL_SELECT_ALL_BY_LESSON_ID =
            "SELECT `attendance`.`id`, `attendance`.`lesson_id`, `attendance`.`user_id`, `attendance`.`mark`, `attendance`.`attended` FROM `attendance` WHERE `attendance`.`lesson_id` = ?";

    /**
     * MySQL select query
     * {@link Attendance} by {@link User} id.
     */
    private static final String SQL_SELECT_BY_LESSON_STUDENT =
            "SELECT `attendance`.`id`, `attendance`.`lesson_id`, `attendance`.`user_id`, `attendance`.`mark`, `attendance`.`attended` FROM `attendance` WHERE `attendance`.`lesson_id` = ? AND `attendance`.`user_id` = ?;";

    /**
     * MySQL select query
     * {@link Attendance} by id.
     */
    private static final String SQL_SELECT_BY_ID =
            "SELECT `attendance`.`id`, `attendance`.`lesson_id`, `attendance`.`user_id`, `attendance`.`mark`, `attendance`.`attended` FROM `attendance` WHERE `attendance`.`id` = ?";

    /**
     * MySQL query to update {@link Attendance}.
     */
    private static final String SQL_UPDATE =
            "UPDATE `attendance` SET `attendance`.`mark` = ?, `attendance`.`attended` = ? WHERE `attendance`.`id` = ?";

    /**
     * MySQL query to delete {@link Attendance}.
     */
    private static final String SQL_DELETE =
            "DELETE FROM `attendance` WHERE `attendance`.`user_id` = ?";

    /**
     * MySQL query to select all {@link Attendance}'s by {@link CourseUser}.
     */
    private static final String SQL_SELECT_BY_COURSE_USER =
            "SELECT `attendance`.`id`, `attendance`.`lesson_id`, `attendance`.`user_id`, `attendance`.`mark`, `attendance`.`attended` FROM `attendance` JOIN lesson l on l.id = attendance.lesson_id WHERE `attendance`.`user_id` = ? AND `l`.`course_id` = ?;";

    @Override
    public boolean save(final Attendance attendance) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntityForSave(ps, attendance);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    attendance.setId(rs.getLong(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating attendance: ", e);
        }
        return false;
    }

    @Override
    public List<Attendance> findAll() throws DaoException {
        throw new UnsupportedOperationException(
                "Reading all attendances is unsupported");
    }

    @Override
    public Attendance findOne(final long id) throws DaoException {
        Attendance attendance = null;
        try (PreparedStatement ps =
                     connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                attendance = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading attendance by id: ",
                    e);
        }
        return attendance;
    }

    @Override
    public List<Attendance> findByLessonId(final long lessonId)
            throws DaoException {
        List<Attendance> attendances = new ArrayList<>();
        try (PreparedStatement ps =
                     connection.prepareStatement(SQL_SELECT_ALL_BY_LESSON_ID)) {
            ps.setLong(1, lessonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance lesson = this.mapToEntity(rs);
                attendances.add(lesson);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all attendances"
                    + " by lesson id: ", e);
        }
        return attendances;
    }

    @Override
    public Attendance findByLessonAndStudentId(final long lessonId,
                                               final long studentId)
            throws DaoException {

        Attendance attendance = null;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_LESSON_STUDENT)) {
            ps.setLong(1, lessonId);
            ps.setLong(2, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                attendance = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all"
                    + " attendances by lesson and student id: ", e);
        }
        return attendance;
    }

    @Override
    public List<Attendance> findByCourseUser(final CourseUser courseUser)
            throws DaoException {
        List<Attendance> attendances = new ArrayList<>();
        try (PreparedStatement ps =
                     connection.prepareStatement(SQL_SELECT_BY_COURSE_USER)) {
            ps.setLong(1, courseUser.getUser().getId());
            ps.setLong(2, courseUser.getCourse().getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance attendance = this.mapToEntity(rs);
                attendances.add(attendance);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all"
                    + " attendances by course user: ", e);
        }
        return attendances;
    }


    @Override
    public boolean update(final Attendance attendance) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntityForUpdate(ps, attendance);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating attendance: ", e);
        }
    }

    @Override
    public boolean delete(final long studentId) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting attendance: ", e);
        }
    }

    private Attendance mapToEntity(final ResultSet rs) throws SQLException {

        LOGGER.debug("Mapping result set to attendance");
        Attendance attendance = Attendance.builder()
                .id(rs.getLong("attendance.id"))
                .lessonId(rs.getLong("attendance.lesson_id"))
                .student(User.builder()
                        .id(rs.getLong("attendance.user_id"))
                        .build())
                .status(AttendanceStatus
                        .resolveStatusById(rs.getInt("attendance.attended")))
                .build();
        Integer mark = rs.getInt("attendance.mark");
        if (rs.wasNull()) {
            mark = null;
        }
        attendance.setMark(mark);
        return attendance;
    }

    private void mapFromEntityForSave(final PreparedStatement ps,
                                      final Attendance attendance)
            throws SQLException {

        LOGGER.debug("Mapping attendance for saving");
        ps.setLong(1, attendance.getLessonId());
        ps.setLong(2, attendance.getStudent().getId());
        this.handleMarkField(3, attendance.getMark(), ps);
        ps.setInt(4, attendance.getStatus().getId());
    }

    private void mapFromEntityForUpdate(final PreparedStatement ps,
                                        final Attendance attendance)
            throws SQLException {

        LOGGER.debug("Mapping attendance for updating");
        this.handleMarkField(1, attendance.getMark(), ps);
        ps.setInt(2, attendance.getStatus().getId());
        ps.setLong(3, attendance.getId());
    }

    private void handleMarkField(final int index, final Integer mark,
                                 final PreparedStatement ps)
            throws SQLException {
        if (mark != null) {
            ps.setInt(index, mark);
        } else {
            ps.setNull(index, Types.NULL);
        }
    }
}
