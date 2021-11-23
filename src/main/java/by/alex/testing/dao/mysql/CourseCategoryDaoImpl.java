package by.alex.testing.dao.mysql;

import by.alex.testing.dao.AbstractDao;
import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.CourseCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseCategoryDaoImpl extends AbstractDao<CourseCategory, Long> implements CourseCategoryDao {

    private static final String SQL_SELECT_ALL =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category`";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` WHERE `course_category`.`id` = ?";

    private static final String SQL_SELECT_ALL_WITH_LIMIT =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` LIMIT ?, ?;";

    private static final String SQL_SELECT_BY_TITLE_WITH_LIMIT =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` WHERE name LIKE ? LIMIT ?, ?;";

    private static final String SQL_SELECT_BY_TITLE =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` WHERE name = ?;";

    private static final String SQL_CREATE =
            "INSERT INTO `course_category`(`name`) VALUE (?);";

    private static final String SQL_UPDATE =
            "UPDATE `course_category` SET `course_category`.name = ? WHERE `course_category`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `course_category` WHERE `course_category`.`id` = ?;";

    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `course_category`;";

    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `course_category` WHERE course_category.name LIKE ?;";

    protected CourseCategoryDaoImpl() {
    }

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
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting category: ", e);
        }
    }

    @Override
    public void create(CourseCategory category) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntity(ps, category);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    category.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating category: ", e);
        }
    }

    @Override
    public void update(CourseCategory category) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntity(ps, category);
            ps.setLong(2, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while creating category: ", e);
        }
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

    @Override
    public Integer count() throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting all courses by search: ", e);
        }
        return count;
    }

    @Override
    public Integer count(String search) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting all courses by search: ", e);
        }
        return count;
    }

    @Override
    public List<CourseCategory> readByTitle(int start, int recOnPage, String search) throws DaoException {
        List<CourseCategory> categories = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TITLE_WITH_LIMIT)) {
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, start);
            ps.setInt(3, recOnPage);
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
    public List<CourseCategory> readAll(int start, int recOnPage) throws DaoException {
        List<CourseCategory> categories = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_WITH_LIMIT)) {
            ps.setInt(1, start);
            ps.setInt(2, recOnPage);
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
    public CourseCategory readByTitle(String name) throws DaoException {
        CourseCategory category = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TITLE)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all categories: ", e);
        }
        return category;
    }
}
