package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.CourseCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseCategoryDaoImpl implements CourseCategoryDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `course_category`.`id`, `course_category`.`name`\n" +
                    "FROM `course_category`";

    private static final String SQL_SELECT_BY_ID =
            SQL_SELECT_ALL + "\n WHERE `course_category`.`id` = ?";

    private static final String SQL_CREATE =
            "INSERT INTO `course_category`(`name`) VALUE (?);";

    private static final String SQL_DELETE =
            "DELETE\n" +
                    "FROM `course_category`\n" +
                    "WHERE `course_category`.`id` = ?;";

    private Connection connection;

    @Override
    public List<CourseCategory> readAll() throws DaoException {
        List<CourseCategory> categories = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseCategory category = this.mapToEntity(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all categories: ", e);
        }
        return categories;
    }

    @Override
    public CourseCategory readById(Long id) throws DaoException {
        CourseCategory category = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                category = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading category by id: ", e);
        }
        return category;
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting category: ", e);
        }
    }

    @Override
    public void create(CourseCategory category) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE)) {
            this.mapFromEntity(ps, category);
        } catch (SQLException e) {
            throw new DaoException("Exception while creating category: ", e);
        }
    }

    @Override
    public void update(CourseCategory category) throws DaoException {
        throw new UnsupportedOperationException("Updating category isn't supported");
    }

    private CourseCategory mapToEntity(ResultSet rs) throws SQLException {
        return CourseCategory.builder()
                .id(rs.getLong("course_category.id"))
                .name(rs.getString("course_category.name"))
                .build();
    }

    private void mapFromEntity(PreparedStatement ps, CourseCategory category) throws SQLException {
        ps.setString(1, category.getName());
    }
}
