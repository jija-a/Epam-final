package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseUserDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseUserDaoImpl implements CourseUserDao {

    private static final String SQL_SELECT_ALL_COURSE_USERS =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status` FROM `course_user` WHERE `course_user`.`course_id` = ?;";

    private static final String SQL_SELECT_ALL_USER_COURSES =
            "SELECT `course_user`.`user_id`, `course_user`.`course_id`, `course_user`.`status` FROM `course_user` WHERE `course_user`.`user_id` = ?;";

    private static final String SQL_DELETE_USER_FROM_COURSE =
            "DELETE FROM `course_user` WHERE `course_user`.`course_id` = ? AND `course_user`.`user_id` = ? AND `course_user`.`status` = ?";

    private static final String SQL_ADD_USER_TO_COURSE =
            "INSERT INTO `course_user`(`course_id`, `user_id`, `status`) VALUES (?, ?, ?);";

    private static final String SQL_UPDATE_COURSE_USER =
            "UPDATE `course_user` SET `course_user`.`course_id` = ?, `course_user`.`user_id` = ?, `course_user`.`status` = ? WHERE `course_user`.`user_id` = ?";

    private final Connection connection;

    public CourseUserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CourseUser> readAllCourseUsers() throws DaoException {
        List<CourseUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_COURSE_USERS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseUser user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all users: ", e);
        }
        return users;
    }

    @Override
    public void removeUserFromCourse(CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_USER_FROM_COURSE)) {
            this.mapFromEntity(ps, courseUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting user: ", e);
        }
    }

    @Override
    public void addUserToCourse(CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_ADD_USER_TO_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, courseUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while creating user: ", e);
        }
    }

    @Override
    public void updateCourseUser(CourseUser courseUser) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_COURSE_USER)) {
            this.mapFromEntity(ps, courseUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating user: ", e);
        }
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
            throw new DaoException("Exception while reading all users: ", e);
        }
        return users;
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
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, CourseUser courseUser) throws SQLException {
        ps.setLong(1, courseUser.getCourse().getId());
        ps.setLong(2, courseUser.getUser().getId());
        ps.setInt(3, courseUser.getStatus().getId());
    }
}