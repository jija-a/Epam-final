package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserCourseStatus;
import by.alex.testing.domain.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractMySqlDao implements UserDao {

    /**
     * Query to create {@link User}.
     */
    private static final String SQL_CREATE =
            "INSERT INTO `user`(`login`, `first_name`, `last_name`, `password`, `role`) VALUES (?, ?, ?, ?, ?);";

    /**
     * Query to select all {@link User}'s.
     */
    private static final String SQL_SELECT_ALL =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user`";

    /**
     * Query to select all {@link User}'s limited.
     */
    private static final String SQL_SELECT_ALL_WITH_LIMIT =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` LIMIT ?, ?;";

    /**
     * Query to select {@link User} by id.
     */
    private static final String SQL_SELECT_BY_ID =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE `user`.`id` = ?;";

    /**
     * Query to select {@link User} by login.
     */
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE `user`.`login` = ?";

    /**
     * Query to select {@link User} by name or login limited.
     */
    private static final String SQL_SELECT_BY_NAME_OR_LOGIN_LIMIT =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE first_name LIKE ? or last_name LIKE ? or login LIKE ? LIMIT ?, ? ";

    /**
     * Query to select {@link User}'s by
     * {@link by.alex.testing.domain.Course} id.
     */
    private static final String SQL_SELECT_BY_COURSE_ID =
            "SELECT `user`.`id`,`user`.`login`,`user`.`first_name`,`user`.`last_name`,`user`.`password`,`user`.`role` FROM user INNER JOIN course_user on user.id = course_user.user_id JOIN course on course.id = course_user.course_id WHERE course.id = ? AND course_user.status = ? GROUP BY `user`.`id`;";

    /**
     * Query to select {@link User}'s by
     * {@link by.alex.testing.domain.Course} id limited.
     */
    private static final String SQL_SELECT_BY_COURSE_ID_LIMIT =
            "SELECT `user`.`id`,`user`.`login`,`user`.`first_name`,`user`.`last_name`,`user`.`password`,`user`.`role` FROM user INNER JOIN course_user on user.id = course_user.user_id JOIN course on course.id = course_user.course_id WHERE course.id = ? AND course_user.status = ? GROUP BY `user`.`id` LIMIT ?, ?;";

    /**
     * Query to select {@link User}'s by
     * {@link by.alex.testing.domain.Course} id and title limited.
     */
    private static final String SQL_SELECT_BY_COURSE_ID_AND_NAME_LIMIT =
            "SELECT `user`.`id`,`user`.`login`,`user`.`first_name`,`user`.`last_name`,`user`.`password`,`user`.`role` FROM user INNER JOIN course_user on user.id = course_user.user_id JOIN course on course.id = course_user.course_id WHERE course.id = ? AND course_user.status = ? AND (first_name LIKE ? or last_name LIKE ? or login LIKE ?) GROUP BY `user`.`id` LIMIT ?, ?;";

    /**
     * Query ti update {@link User}.
     */
    private static final String SQL_UPDATE =
            "UPDATE `user` SET `user`.`login` = ?, `user`.`first_name` = ?, `user`.`last_name` = ?, `user`.`password` = ?, `user`.`role` = ? WHERE `user`.`id` = ?;";

    /**
     * Query to delete {@link User}.
     */
    private static final String SQL_DELETE =
            "DELETE FROM `user` WHERE user.`id` = ?;";

    /**
     * Query to count all {@link User}'s.
     */
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `user`;";

    /**
     * Query to count all {@link User}'s by name or login.
     */
    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `user` WHERE first_name LIKE ? or last_name LIKE ? or login LIKE ?;";

    protected UserDaoImpl() {
    }

    @Override
    public boolean save(final User user) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, user);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating user: ", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all users: ", e);
        }
        return users;
    }

    @Override
    public List<User> findAll(final int start, final int total)
            throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_ALL_WITH_LIMIT)) {
            ps.setInt(1, start);
            ps.setInt(2, total);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " all users with limit: ", e);
        }
        return users;
    }

    @Override
    public User findOne(final long id) throws DaoException {
        User user = null;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading user by id: ", e);
        }
        return user;
    }

    @Override
    public User findByLogin(final String login) throws DaoException {
        User user = null;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading user "
                    + "by login: ", e);
        }
        return user;
    }

    @Override
    public List<User> findBySearchRequest(final int start,
                                          final int total,
                                          final String search)
            throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_NAME_OR_LOGIN_LIMIT)) {
            String param = createLikeParameter(search);
            ps.setString(1, param);
            ps.setString(2, param);
            ps.setString(3, param);
            ps.setInt(4, start);
            ps.setInt(5, total);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading user "
                    + "by name or login with limit: ", e);
        }
        return users;
    }

    @Override
    public List<User> findByCourseId(final Long id) throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_COURSE_ID)) {
            ps.setLong(1, id);
            ps.setInt(2, UserCourseStatus.ON_COURSE.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading users"
                    + " by course id: ", e);
        }
        return users;
    }

    @Override
    public List<User> findByCourseId(final int start,
                                     final int total,
                                     final long courseId)
            throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_COURSE_ID_LIMIT)) {
            ps.setLong(1, courseId);
            ps.setLong(2, UserCourseStatus.ON_COURSE.getId());
            ps.setInt(3, start);
            ps.setInt(4, total);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " users by course id with limit: ", e);
        }
        return users;
    }

    @Override
    public List<User> findByCourseId(final int start,
                                     final int total,
                                     final long courseId,
                                     final String search)
            throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_SELECT_BY_COURSE_ID_AND_NAME_LIMIT)) {
            String param = createLikeParameter(search);
            ps.setLong(1, courseId);
            ps.setLong(2, UserCourseStatus.ON_COURSE.getId());
            ps.setString(3, param);
            ps.setString(4, param);
            ps.setString(5, param);
            ps.setInt(6, start);
            ps.setInt(7, total);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = this.mapToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading"
                    + " user by name and course id with limit: ", e);
        }
        return users;
    }

    @Override
    public boolean delete(final long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting user: ", e);
        }
    }

    @Override
    public boolean update(final User user) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, user);
            ps.setLong(6, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating user: ", e);
        }
    }

    @Override
    public Integer count() throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_ALL)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while count all users: ", e);
        }
        return count;
    }

    @Override
    public Integer count(final String search) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection
                .prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            String param = createLikeParameter(search);
            ps.setString(1, param);
            ps.setString(2, param);
            ps.setString(3, param);
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

    private User mapToEntity(final ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("user.id"))
                .login(rs.getString("user.login"))
                .firstName(rs.getString("user.first_name"))
                .lastName(rs.getString("user.last_name"))
                .password(rs.getString("user.password").toCharArray())
                .role(UserRole.resolveRoleById(rs.getInt("user.role")))
                .build();
    }

    private void mapFromEntity(final PreparedStatement ps,
                               final User user)
            throws SQLException {
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(4, String.valueOf(user.getPassword()));
        ps.setInt(5, user.getRole().getId());
    }
}
