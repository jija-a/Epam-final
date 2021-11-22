package by.alex.testing.dao.mysql;

import by.alex.testing.dao.AbstractDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user`";

    private static final String SQL_SELECT_ALL_WITH_LIMIT =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` LIMIT ?, ?;";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE `user`.`id` = ?;";

    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE `user`.`login` = ?";

    private static final String SQL_SELECT_BY_COURSE_ID =
            "SELECT `user`.`id`,`user`.`login`,`user`.`first_name`,`user`.`last_name`,`user`.`password`,`user`.`role` FROM user INNER JOIN course_user on user.id = course_user.user_id JOIN course on course.id = course_user.course_id WHERE course.id = ?;";

    private static final String SQL_SELECT_BY_NAME =
            "SELECT `user`.`id`, `user`.`login`, `user`.`first_name`, `user`.`last_name`, `user`.`password`, `user`.`role` FROM `user` WHERE first_name LIKE ? or last_name LIKE ? or login LIKE ? LIMIT ?, ? ";

    private static final String SQL_CREATE =
            "INSERT INTO `user`(`login`, `first_name`, `last_name`, `password`, `role`) VALUES (?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `user` SET `user`.`login` = ?, `user`.`first_name` = ?, `user`.`last_name` = ?, `user`.`password` = ?, `user`.`role` = ? WHERE `user`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `user` WHERE user.`id` = ?;";

    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `user`;";

    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `user` WHERE first_name LIKE ? or last_name LIKE ? or login LIKE ?;";

    protected UserDaoImpl() {
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
    public List<User> readAll(int start, int total) throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_WITH_LIMIT)) {
            ps.setInt(1, start);
            ps.setInt(2, total);
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
    public List<User> readByName(int start, int total, String name) throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_NAME)) {
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");
            ps.setInt(3, start);
            ps.setInt(4, total);
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
    public Integer count() throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all users: ", e);
        }
        return count;
    }

    @Override
    public Integer count(String search) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            String param = "%" + search + "%";
            ps.setString(1, param);
            ps.setString(2, param);
            ps.setString(3, param);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all users: ", e);
        }
        return count;
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
