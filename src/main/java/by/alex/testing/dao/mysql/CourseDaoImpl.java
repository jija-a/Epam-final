package by.alex.testing.dao.mysql;

import by.alex.testing.dao.CourseDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl extends AbstractMySqlDao implements CourseDao {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseDaoImpl.class);

    private static final String SQL_CREATE =
            "INSERT INTO `course`(`name`, `user_id`, `course_category_id`) VALUES (?, ?, ?);";

    private static final String SQL_SELECT_ALL =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course`";

    private static final String SQL_SELECT_BY_ID =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`id` = ?;";

    private static final String SQL_SELECT_ALL_WITH_LIMIT =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` LIMIT ?, ?;";

    private static final String SQL_SELECT_BY_TITLE_WITH_LIMIT =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`name` LIKE ? LIMIT ?, ?;";

    private static final String SQL_SELECT_BY_OWNER_ID_WITH_LIMIT =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`user_id` = ? LIMIT ?, ?;";

    private static final String SQL_SELECT_BY_OWNER_ID_AND_NAME =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE `course`.`user_id` = ? AND `course`.`name` = ?;";

    private static final String SQL_SELECT_ALL_EXCLUDING =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE NOT EXISTS(SELECT `course_user`.`course_id` FROM `course_user` WHERE `course`.`id` = `course_user`.`course_id` AND `course_user`.`user_id` = ?) LIMIT ?,?;";

    private static final String SQL_SELECT_ALL_EXCLUDING_WITH_SEARCH_REQ =
            "SELECT `course`.`id`, `course`.`name`, `course`.`user_id`, `course`.`course_category_id` FROM `course` WHERE NOT EXISTS(SELECT `course_user`.`course_id` FROM `course_user` WHERE `course`.`id` = `course_user`.`course_id` AND `course_user`.`user_id` = ?) AND `course`.`name` LIKE ? LIMIT ?,?;";

    private static final String SQL_UPDATE =
            "UPDATE `course` SET `course`.`name` = ?, `course`.`user_id` = ?, `course`.`course_category_id` = ? WHERE `course`.`id` = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM `course` WHERE `course`.`id` = ?;";

    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM `course`;";

    private static final String SQL_COUNT_ALL_BY_NAME =
            "SELECT COUNT(*) FROM `course` WHERE `course`.`name` LIKE ?;";

    private static final String SQL_COUNT_ALL_BY_OWNER =
            "SELECT COUNT(*) FROM `course` WHERE `course`.`user_id` = ?;";

    private static final String SQL_COUNT_AVAILABLE_COURSES =
            "SELECT COUNT(*) FROM `course` WHERE NOT EXISTS(SELECT `course_user`.`course_id` FROM `course_user` WHERE `course`.`id` = `course_user`.`course_id` AND `course_user`.`user_id` = ?);";

    private static final String SQL_COUNT_AVAILABLE_COURSES_WITH_SEARCH =
            "SELECT COUNT(*) FROM `course` WHERE NOT EXISTS(SELECT `course_user`.`course_id` FROM `course_user` WHERE `course`.`id` = `course_user`.`course_id` AND `course_user`.`user_id` = ?) AND `course`.`name` LIKE ?;";

    protected CourseDaoImpl() {
    }

    @Override
    public boolean save(Course course) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                Statement.RETURN_GENERATED_KEYS)) {

            this.mapFromEntityForSave(ps, course);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    course.setId(rs.getLong(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while creating course: ", e);
        }
        return false;
    }

    @Override
    public List<Course> findAll() throws DaoException {
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
    public List<Course> findAll(int start, int recOnPage) throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_WITH_LIMIT)) {
            ps.setInt(1, start);
            ps.setInt(2, recOnPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all courses with limit: ", e);
        }
        return courses;
    }

    @Override
    public List<Course> findExcludingUserCourses(long studentId, int start, int recordsPerPage) throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_EXCLUDING)) {
            ps.setLong(1, studentId);
            ps.setInt(2, start);
            ps.setInt(3, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all courses user does not enter: ", e);
        }
        return courses;
    }

    @Override
    public List<Course> findExcludingUserCourses(long studentId, int start, int recordsPerPage, String search)
            throws DaoException {

        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_EXCLUDING_WITH_SEARCH_REQ)) {
            ps.setLong(1, studentId);
            ps.setString(2, createLikeParameter(search));
            ps.setInt(3, start);
            ps.setInt(4, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading all courses user does not enter with limit: ", e);
        }
        return courses;
    }

    @Override
    public List<Course> findByOwnerId(Long userId, int start, int recOnPage) throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_OWNER_ID_WITH_LIMIT)) {
            ps.setLong(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, recOnPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading courses by owner id with limit: ", e);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByTitle(String title, int start, int recOnPage) throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_TITLE_WITH_LIMIT)) {
            ps.setString(1, createLikeParameter(title));
            ps.setInt(2, start);
            ps.setInt(3, recOnPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = this.mapToEntity(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading courses by title with limit: ", e);
        }
        return courses;
    }

    @Override
    public Course findOne(long id) throws DaoException {
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
    public Course findByOwnerIdAndName(long userId, String courseName) throws DaoException {
        Course course = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_OWNER_ID_AND_NAME)) {
            ps.setLong(1, userId);
            ps.setString(2, courseName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course = this.mapToEntity(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while reading courses by owner id with limit: ", e);
        }
        return course;
    }

    @Override
    public boolean update(Course course) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            this.mapFromEntityForUpdate(ps, course);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating course: ", e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting course: ", e);
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
            throw new DaoException("Exception while counting all courses: ", e);
        }
        return count;
    }

    @Override
    public Integer count(String search) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_BY_NAME)) {
            ps.setString(1, createLikeParameter(search));
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
    public Integer countOwnerCourses(long userId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ALL_BY_OWNER)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting all courses by user id: ", e);
        }
        return count;
    }

    @Override
    public Integer countAvailableCourses(long studentId) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_AVAILABLE_COURSES)) {
            ps.setLong(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting available courses: ", e);
        }
        return count;
    }

    @Override
    public Integer countAvailableCourses(long studentId, String search) throws DaoException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SQL_COUNT_AVAILABLE_COURSES_WITH_SEARCH)) {
            ps.setLong(1, studentId);
            ps.setString(2, createLikeParameter(search));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while counting available courses with limit: ", e);
        }
        return count;
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

    protected void mapFromEntityForSave(PreparedStatement ps, Course course) throws SQLException {
        logger.debug("Mapping course for saving");
        ps.setString(1, course.getName());
        ps.setLong(2, course.getOwner().getId());
        ps.setLong(3, course.getCategory().getId());
    }

    protected void mapFromEntityForUpdate(PreparedStatement ps, Course course) throws SQLException {
        logger.debug("Mapping course for updating");
        ps.setString(1, course.getName());
        ps.setLong(2, course.getOwner().getId());
        ps.setLong(3, course.getCategory().getId());
        ps.setLong(4, course.getId());
    }
}
