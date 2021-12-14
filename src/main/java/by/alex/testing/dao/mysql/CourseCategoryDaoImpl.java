package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseCategoryDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.CourseCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseCategoryDaoImpl extends AbstractMySqlDao implements CourseCategoryDao {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseCategoryDaoImpl.class);

    private static final String SQL_CREATE =
            "INSERT INTO `course_category`(`name`) VALUE (?);";

    private static final String SQL_READ_ALL =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category`";

    private static final String SQL_READ_BY_ID =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` WHERE `course_category`.`id` = ?";

    private static final String SQL_READ_ALL_WITH_LIMIT =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` LIMIT ?, ?;";

    private static final String SQL_READ_BY_TITLE_WITH_LIMIT =
            "SELECT `course_category`.`id`, `course_category`.`name` FROM `course_category` WHERE `course_category`.`name` LIKE ? LIMIT ?, ?;";

    private static final String SQL_UPDATE =
            "UPDATE `course_category` SET `course_category`.name = ? WHERE `course_category`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `course_category` WHERE `course_category`.`id` = ?;";

    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `course_category`;";

    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `course_category` WHERE `course_category`.`name` LIKE ?;";

    private static final String SQL_EXISTS =
            "SELECT `course_category`.`id` FROM `course_category` WHERE `course_category`.`name` = ?;";

    protected CourseCategoryDaoImpl() {
    }

    @Override
    public boolean save(CourseCategory category) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {
            this.mapFromEntityForSave(ps, category);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    category.setId(rs.getLong(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating category: ", e);
        }
        return false;
    }

    @Override
    public List<CourseCategory> findAll() throws DaoException {
        List<CourseCategory> categories = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_READ_ALL)) {
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
    public List<CourseCategory> findAll(int start, int recOnPage) throws DaoException {
        List<CourseCategory> categories = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_READ_ALL_WITH_LIMIT)) {
            ps.setInt(1, start);
            ps.setInt(2, recOnPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseCategory category = this.mapToEntity(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all categories with limit: ", e);
        }
        return categories;
    }

    @Override
    public CourseCategory findOne(long id) throws DaoException {
        CourseCategory category = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_READ_BY_ID)) {
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
    public List<CourseCategory> findByTitle(int start, int recOnPage, String search) throws DaoException {
        List<CourseCategory> categories = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_READ_BY_TITLE_WITH_LIMIT)) {
            ps.setString(1, createLikeParameter(search));
            ps.setInt(2, start);
            ps.setInt(3, recOnPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseCategory category = this.mapToEntity(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading categories by title with limit: ", e);
        }
        return categories;
    }

    @Override
    public boolean update(CourseCategory category) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntityForUpdate(ps, category);
            ps.setLong(2, category.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating category: ", e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting category: ", e);
        }
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
            throw new DaoException("Exception while counting all categories: ", e);
        }
        return count;
    }

    @Override
    public Integer count(String title) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            ps.setString(1, createLikeParameter(title));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting all categories by title: ", e);
        }
        return count;
    }

    @Override
    public boolean exists(String title) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while finding out if category exists: ", e);
        }
        return false;
    }

    private CourseCategory mapToEntity(ResultSet rs) throws SQLException {
        return CourseCategory.builder()
                .id(rs.getLong("course_category.id"))
                .name(rs.getString("course_category.name"))
                .build();
    }

    private void mapFromEntityForSave(PreparedStatement ps, CourseCategory category) throws SQLException {
        logger.debug("Mapping course category for saving");
        ps.setString(1, category.getName());
    }

    private void mapFromEntityForUpdate(PreparedStatement ps, CourseCategory category) throws SQLException {
        logger.debug("Mapping course category for updating");
        ps.setString(1, category.getName());
        ps.setLong(2, category.getId());
    }
}
