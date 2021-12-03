package by.alex.testing.dao.mysql;

import by.alex.testing.dao.AbstractDao;
import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseUserDaoImpl extends AbstractDao<CourseUser, Long> implements CourseUserDao {

    private static final String SQL_CREATE =
            "INSERT INTO `course_user`(`course_id`, `user_id`, `status`, rating) VALUES (?, ?, ?, ?);";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.rating FROM `course_user` WHERE `course_user`.`course_id` = ? AND `course_user`.`user_id` = ?;";

    private static final String SQL_SELECT_COURSE_USER_BY_USER_ID =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.rating FROM `course_user` WHERE `course_user`.`user_id` = ?;";

    private static final String SQL_SELECT_ALL_REQUESTS =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.rating FROM `course_user` JOIN course c on c.id = course_user.course_id WHERE c.user_id = ? AND course_user.status = 0 LIMIT ?, ?;";

    private static final String SQL_SELECT_ALL_USER_COURSES =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status`, `course_user`.rating FROM `course_user` WHERE `course_user`.`user_id` = ?;";

    private static final String SQL_UPDATE =
            "UPDATE `course_user` SET `course_user`.`status` = ? WHERE `course_user`.`user_id` = ? AND `course_user`.`course_id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `course_user` WHERE `course_user`.`course_id` = ? AND `course_user`.`user_id` = ? AND `course_user`.`status` = ?";

    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `course_user` WHERE course_id = ? AND `course_user`.`status` = 1";

    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `course_user` JOIN user u on u.id = course_user.user_id WHERE course_user.course_id = ? AND u.first_name LIKE ? OR u.last_name LIKE ? or u.login LIKE ? AND `course_user`.`status` = 1;";

    private static final String SQL_COUNT_ALL_REQUESTS =
            "SELECT COUNT(*) FROM `course_user` JOIN course c on c.id = course_user.course_id WHERE c.user_id = ? AND course_user.status = 0";

    protected CourseUserDaoImpl() {
    }

    @Override
    public boolean create(CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {

            this.mapFromEntity(ps, courseUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating course user: ", e);
        }
    }

    @Override
    public List<CourseUser> readAll() throws DaoException {
        throw new UnsupportedOperationException("Reading all course users is unsupported");
    }

    @Override
    public CourseUser readById(Long userId) throws DaoException {
        CourseUser courseUser = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_COURSE_USER_BY_USER_ID)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                courseUser = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading user courses by user id: ", e);
        }
        return courseUser;
    }

    @Override
    public CourseUser readById(long courseId, long userId) throws DaoException {
        CourseUser user = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, courseId);
            ps.setLong(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading course user by id: ", e);
        }
        return user;
    }

    @Override
    public List<CourseUser> readAllUserCourses(Long userId) throws DaoException {
        List<CourseUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_USER_COURSES)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseUser user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all user courses: ", e);
        }
        return users;
    }

    @Override
    public List<CourseUser> readAllRequests(int start, int recOnPage, long userId) throws DaoException {
        List<CourseUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_REQUESTS)) {
            ps.setLong(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, recOnPage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseUser user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all course requests: ", e);
        }
        return users;
    }

    @Override
    public boolean update(CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            ps.setLong(1, courseUser.getStatus().getId());
            ps.setLong(2, courseUser.getUser().getId());
            ps.setLong(3, courseUser.getCourse().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating course user: ", e);
        }
    }

    @Override
    public boolean delete(CourseUser courseUser) throws DaoException {
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
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException("Deleting by id is unsupported");
    }

    @Override
    public Integer count(long courseId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all course users: ", e);
        }
        return count;
    }

    @Override
    public Integer count(long courseId, String search) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            String param = "%" + search + "%";
            ps.setLong(1, courseId);
            ps.setString(2, param);
            ps.setString(3, param);
            ps.setString(4, param);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all course users: ", e);
        }
        return count;
    }

    @Override
    public int countRequests(long teacherId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_REQUESTS)) {
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

    private CourseUser mapToEntity(ResultSet rs) throws SQLException {
        return CourseUser.builder()
                .user(User.builder()
                        .id(rs.getLong("course_user.user_id"))
                        .build())
                .course(Course.builder()
                        .id(rs.getLong("course_user.course_id"))
                        .build())
                .status(UserCourseStatus.resolveStatusById(
                        rs.getInt("course_user.status")))
                .rating(rs.getDouble("course_user.rating"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, CourseUser courseUser) throws SQLException {
        ps.setLong(1, courseUser.getCourse().getId());
        ps.setLong(2, courseUser.getUser().getId());
        ps.setInt(3, courseUser.getStatus().getId());
        if (courseUser.getRating() != null) {
            ps.setDouble(4, courseUser.getRating());
        } else {
            ps.setNull(4, Types.NULL);
        }
    }
}
