package by.alex.testing.dao.mysql;

import by.alex.testing.dao.AbstractDao;
import by.alex.testing.dao.AttendanceDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import by.alex.testing.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDaoImpl extends AbstractDao<Attendance, Long> implements AttendanceDao {

    private static final String SQL_CREATE =
            "INSERT INTO `attendance` (`lesson_id`, `user_id`, `mark`, `attended`) VALUES (?, ?, ?, ?);";

    private static final String SQL_SELECT_ALL_BY_LESSON_ID =
            "SELECT `attendance`.`id`, `attendance`.`lesson_id`, `attendance`.`user_id`, `attendance`.`mark`, `attendance`.`attended` FROM `attendance` WHERE `attendance`.`lesson_id` = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `attendance`.`id`, `attendance`.`lesson_id`, `attendance`.`user_id`, `attendance`.`mark`, `attendance`.`attended` FROM `attendance` WHERE `attendance`.`id` = ?";

    private static final String SQL_UPDATE =
            "UPDATE `attendance` SET `attendance`.`mark` = ?, `attendance`.`attended` = ? WHERE `attendance`.`id` = ?";

    @Override
    public boolean create(Attendance entity) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, entity.getLessonId());
            ps.setLong(2, entity.getStudent().getId());
            handleMark(ps, entity, 3);
            ps.setInt(4, entity.getStatus().getId());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating attendance by id: ", e);
        }
    }

    @Override
    public List<Attendance> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all attendances is unsupported");
    }

    @Override
    public Attendance readById(Long id) throws DaoException {
        Attendance attendance = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                attendance = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading attendance by id: ", e);
        }
        return attendance;
    }

    @Override
    public List<Attendance> readByLessonId(long lessonId) throws DaoException {
        List<Attendance> lessons = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_BY_LESSON_ID)) {
            ps.setLong(1, lessonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance lesson = this.mapToEntity(rs);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all lessons by course id: ", e);
        }
        return lessons;
    }

    @Override
    public boolean update(Attendance attendance) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, attendance);
            ps.setLong(3, attendance.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all lessons by course id: ", e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException("Deleting attendance is unsupported");
    }

    private Attendance mapToEntity(ResultSet rs) throws SQLException {
        return Attendance.builder()
                .id(rs.getLong("attendance.id"))
                .lessonId(rs.getLong("attendance.lesson_id"))
                .student(User.builder().id(rs.getLong("attendance.user_id")).build())
                .mark(rs.getInt("attendance.mark"))
                .status(AttendanceStatus.resolveStatusById(rs.getInt("attendance.attended")))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Attendance attendance) throws SQLException {
        handleMark(ps, attendance, 1);
        ps.setInt(2, attendance.getStatus().getId());
    }

    private void handleMark(PreparedStatement ps, Attendance attendance, int index)
            throws SQLException {

        if (attendance.getMark() != null) {
            ps.setInt(index, attendance.getMark());
        } else {
            ps.setNull(index, Types.NULL);
        }
    }
}
