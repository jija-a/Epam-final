package by.alex.testing.dao.mysql;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user`";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE `user`.`id` = ?;";

    private static final String SQL_CREATE =
            "INSERT INTO `user`(`login`, `first_name`, `last_name`, `password`, `role`) VALUES (?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `user` SET `user`.`login` = ?, `user`.`first_name` = ?, `user`.`last_name` = ?, `user`.`password` = ?, `user`.`role` = ? WHERE `user`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `user` WHERE user.`id` = ?;";

    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE `user`.`login` = ?";

    private static final String SQL_SELECT_BY_COURSE_ID =
            "SELECT `user`.`id`,`user`.`login`,`user`.`first_name`,`user`.`last_name`,`user`.`password`,`user`.`role` FROM user INNER JOIN course_user on user.id = course_user.user_id JOIN course on course.id = course_user.course_id WHERE course.id = ?;";

    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> readAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL)) {
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
    public User readById(Long id) throws DaoException {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
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
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting user: ", e);
        }
    }

    @Override
    public void create(User user) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, user);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating user: ", e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, user);
            ps.setLong(6, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating user: ", e);
        }
    }

    @Override
    public User readByLogin(String login) throws DaoException {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading user by login: ", e);
        }
        return user;
    }

    @Override
    public List<User> readByCourseId(Long id) throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_COURSE_ID)) {
            ps.setLong(1, id);
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

    private User mapToEntity(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("user.id"))
                .login(rs.getString("user.login"))
                .firstName(rs.getString("user.first_name"))
                .lastName(rs.getString("user.last_name"))
                .password(rs.getString("user.password").toCharArray())
                .role(UserRole.resolveRoleById(rs.getInt("user.role")))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(4, String.valueOf(user.getPassword()));
        ps.setInt(5, user.getRole().getId());
    }
}
