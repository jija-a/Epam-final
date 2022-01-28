package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CourseUserDaoImpl extends AbstractMySqlDao implements CourseUserDao {

    /**
     * Query to create course user.
     */
    private static final String SQL_CREATE =
            "INSERT INTO `course_user`(`course_id`, `user_id`, `status`, `rating`, `attendance_percent`) VALUES (?, ?, ?, ?, ?);";

    /**
     * Query to select course user by user and course id.
     */
    private static final String SQL_SELECT_BY_USER_AND_COURSE_ID =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.`rating`, `course_user`.`attendance_percent` FROM `course_user` WHERE `course_user`.`user_id` = ? AND `course_user`.`course_id` = ?;";

    /**
     * Query to select all user requests on courses.
     */
    private static final String SQL_SELECT_ALL_REQUESTS =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.`rating`, `course_user`.`attendance_percent` FROM `course_user` JOIN `course` `c` on `c`.`id` = `course_user`.`course_id` WHERE `c`.`user_id` = ? AND `course_user`.`status` = 0 LIMIT ?, ?;";

    /**
     * Query to select all user courses.
     */
    private static final String SQL_SELECT_ALL_USER_COURSES =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.`rating`, `course_user`.`attendance_percent` FROM `course_user` WHERE `course_user`.`user_id` = ? AND `course_user`.`status` = ?;";

    /**
     * Query to select user courses limited.
     */
    private static final String SQL_SELECT_ALL_USER_COURSES_WITH_LIMIT =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.`rating`, `course_user`.`attendance_percent` FROM `course_user` WHERE `course_user`.`user_id` = ? AND `course_user`.`status` = ? LIMIT ?, ?;";

    /**
     * Query to update course user.
     */
    private static final String SQL_UPDATE =
            "UPDATE `course_user` SET `course_user`.`status` = ?, `course_user`.`rating` = ?, `course_user`.`attendance_percent` = ? WHERE `course_user`.`user_id` = ? AND `course_user`.`course_id` = ?;";

    /**
     * Query to delete course user.
     */
    private static final String SQL_DELETE =
            "DELETE FROM `course_user` WHERE `course_user`.`course_id` = ? AND `course_user`.`user_id` = ? AND `course_user`.`status` = ?";

    /**
     * Query to count all course users by course id.
     */
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `course_user` WHERE `course_user`.`course_id` = ? AND `course_user`.`status` = 1";

    /**
     * Query to count all course users by name or login.
     */
    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `course_user` JOIN `user` `u` on `u`.`id` = `course_user`.`user_id` WHERE `course_user`.`course_id` = ? AND (`u`.`first_name` LIKE ? OR `u`.`last_name` LIKE ? or `u`.`login` LIKE ?) AND `course_user`.`status` = 1;";

    /**
     * Query to count all requests on course.
     */
    private static final String SQL_COUNT_ALL_REQUESTS =
            "SELECT COUNT(*) FROM `course_user` JOIN `course` `c` on `c`.`id` = `course_user`.`course_id` WHERE `c`.`user_id` = ? AND `course_user`.`status` = 0";

    /**
     * Query to count all student courses.
     */
    private static final String SQL_COUNT_STUDENT_COURSES =
            "SELECT COUNT(*) FROM `course_user` WHERE `course_user`.`user_id` = ? AND `course_user`.`status` = ?;";

    @Override
    public boolean save(final CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {

            this.mapFromEntityForSave(ps, courseUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating course user: ", e);
        }
    }

    @Override
    public List<CourseUser> findAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all is unsupported");
    }

    @Override
    public CourseUser findOne(final long userId) throws DaoException {
        throw new UnsupportedOperationException("Reading by id is unsupported");
    }

    @Override
    public CourseUser findByUserAndCourseId(final long userId,
                                            final long courseId)
            throws DaoException {
        CourseUser courseUser = null;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_USER_AND_COURSE_ID)) {
            ps.setLong(1, userId);
            ps.setLong(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                courseUser = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading course"
                    + " user by user id: ", e);
        }
        return courseUser;
    }

    @Override
    public List<CourseUser> findByUserIdAndStatus(final Long userId,
                                                  final UserCourseStatus status)
            throws DaoException {
        List<CourseUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL_USER_COURSES)) {
            ps.setLong(1, userId);
            ps.setInt(2, status.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseUser user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading "
                    + "all user courses: ", e);
        }
        return users;
    }

    @Override
    public List<CourseUser> findByUserIdAndStatus(final long studentId,
                                                  final UserCourseStatus status,
                                                  final int start,
                                                  final int recordsPerPage)
            throws DaoException {
        List<CourseUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL_USER_COURSES_WITH_LIMIT)) {
            ps.setLong(1, studentId);
            ps.setInt(2, status.getId());
            ps.setInt(3, start);
            ps.setInt(4, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseUser user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " all user courses: ", e);
        }
        return users;
    }

    @Override
    public List<CourseUser> findRequests(final int start,
                                         final int recOnPage,
                                         final long userId)
            throws DaoException {
        List<CourseUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL_REQUESTS)) {
            ps.setLong(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, recOnPage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseUser user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " all course requests: ", e);
        }
        return users;
    }

    @Override
    public boolean update(final CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntityForUpdate(ps, courseUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating course user: ", e);
        }
    }

    @Override
    public boolean delete(final CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, courseUser.getCourse().getId());
            ps.setLong(2, courseUser.getUser().getId());
            ps.setLong(3, courseUser.getStatus().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting course user: ", e);
        }
    }

    @Override
    public boolean delete(final long id) throws DaoException {
        throw new UnsupportedOperationException("Deleting by id is"
                + " unsupported");
    }

    @Override
    public Integer count(final long courseId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_ALL)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while"
                    + " reading all course users: ", e);
        }
        return count;
    }

    @Override
    public Integer count(final long courseId, final String search)
            throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            String param = createLikeParameter(search);
            ps.setLong(1, courseId);
            ps.setString(2, param);
            ps.setString(3, param);
            ps.setString(4, param);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while "
                    + "reading all course users: ", e);
        }
        return count;
    }

    @Override
    public int countRequests(final long teacherId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_ALL_REQUESTS)) {
            ps.setLong(1, teacherId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting requests: ", e);
        }
        return count;
    }

    @Override
    public int countStudentCourses(final long studentId,
                                   final UserCourseStatus status)
            throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_STUDENT_COURSES)) {
            ps.setLong(1, studentId);
            ps.setInt(2, status.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting requests: ", e);
        }
        return count;
    }

    private CourseUser mapToEntity(final ResultSet rs) throws SQLException {
        CourseUser courseUser = CourseUser.builder()
                .user(User.builder()
                        .id(rs.getLong("course_user.user_id"))
                        .build())
                .course(Course.builder()
                        .id(rs.getLong("course_user.course_id"))
                        .build())
                .status(UserCourseStatus.resolveStatusById(
                        rs.getInt("course_user.status")))
                .build();
        Double rating = rs.getDouble("course_user.rating");
        if (rs.wasNull()) {
            rating = null;
        }
        Double percent = rs.getDouble("course_user.attendance_percent");
        if (rs.wasNull()) {
            percent = null;
        }
        courseUser.setAttendancePercent(percent);
        courseUser.setRating(rating);
        return courseUser;
    }

    private void mapFromEntityForSave(final PreparedStatement ps,
                                      final CourseUser courseUser)
            throws SQLException {
        ps.setLong(1, courseUser.getCourse().getId());
        ps.setLong(2, courseUser.getUser().getId());
        ps.setInt(3, courseUser.getStatus().getId());
        this.handleRating(4, courseUser.getRating(), ps);
        this.handleAttendancePercent(5, courseUser.getAttendancePercent(), ps);
    }

    private void mapFromEntityForUpdate(final PreparedStatement ps,
                                        final CourseUser courseUser)
            throws SQLException {
        ps.setInt(1, courseUser.getStatus().getId());
        this.handleRating(2, courseUser.getRating(), ps);
        this.handleAttendancePercent(3, courseUser.getAttendancePercent(), ps);
        ps.setLong(4, courseUser.getUser().getId());
        ps.setLong(5, courseUser.getCourse().getId());
    }

    private void handleRating(final int index,
                              final Double rating,
                              final PreparedStatement ps)
            throws SQLException {
        if (rating != null) {
            ps.setDouble(index, rating);
        } else {
            ps.setNull(index, Types.NULL);
        }
    }

    private void handleAttendancePercent(final int index,
                                         final Double attendancePercent,
                                         final PreparedStatement ps)
            throws SQLException {
        if (attendancePercent != null) {
            ps.setDouble(index, attendancePercent);
        } else {
            ps.setNull(index, Types.NULL);
        }
    }
}
