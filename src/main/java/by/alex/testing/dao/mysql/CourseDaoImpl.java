package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course`";

    private static final String SQL_SELECT_BY_TITLE =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`name` LIKE ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`id` = ?;";

    private static final String SQL_SELECT_BY_OWNER_ID =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`user_id` = ?;";

    private static final String SQL_CREATE =
            "INSERT INTO `course`(`name`, `user_id`, `course_category_id`) VALUES (?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE `course` SET `course`.`name` = ?, `course`.`user_id` = ?, `course`.`course_category_id` = ? WHERE `course`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `course` WHERE `course`.`id` = ?;";

    private final Connection connection;

    public CourseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Course> readAll() throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all courses: ", e);
        }
        return courses;
    }

    @Override
    public List<Course> readCourseByTitle(String title) throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TITLE)) {
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading courses by title: ", e);
        }
        return courses;
    }

    @Override
    public List<Course> readByOwnerId(Long userId) throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_OWNER_ID)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading courses by owner id: ", e);
        }
        return courses;
    }

    @Override
    public Course readById(Long id) throws DaoException {
        Course course = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                course = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading course by id: ", e);
        }
        return course;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting course: ", e);
        }
    }

    @Override
    public void create(Course course) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, course);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    course.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating course: ", e);
        }
    }

    @Override
    public void update(Course course) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, course);
            ps.setLong(4, course.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating course: ", e);
        }
    }

    private Course mapToEntity(ResultSet rs) throws SQLException {
        return Course.builder()
                .id(rs.getLong("course.id"))
                .name(rs.getString("course.name"))
                .owner(
                        User.builder()
                                .id(rs.getLong("course.user_id"))
                                .build()
                )
                .category(
                        CourseCategory.builder()
                                .id(rs.getLong("course.course_category_id"))
                                .build())
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, Course course) throws SQLException {
        ps.setString(1, course.getName());
        ps.setLong(2, course.getOwner().getId());
        ps.setLong(3, course.getCategory().getId());
    }
}
