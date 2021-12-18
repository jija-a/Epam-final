package by.alex.testing.dao;

import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.UserCourseStatus;

import java.util.List;

/**
 * Dao interface for {@link CourseUser}.
 */
public interface CourseUserDao extends BaseDao<CourseUser> {

    /**
     * Method allows finding {@link CourseUser}
     * by {@link by.alex.testing.domain.User} and
     * {@link by.alex.testing.domain.Course} id.
     *
     * @param userId   {@link by.alex.testing.domain.User} id
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @return {@link CourseUser}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    CourseUser findByUserAndCourseId(long userId, long courseId)
            throws DaoException;

    /**
     * Method allows finding {@link CourseUser}
     * by {@link by.alex.testing.domain.User} id
     * and they {@link UserCourseStatus} on it.
     *
     * @param userId {@link by.alex.testing.domain.User} id
     * @param status {@link UserCourseStatus}
     * @return {@link List} of {@link CourseUser}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<CourseUser> findByUserIdAndStatus(Long userId, UserCourseStatus status)
            throws DaoException;

    /**
     * Method allows finding {@link CourseUser} limited with start and
     * records per page,
     * by {@link by.alex.testing.domain.User} id
     * and they {@link UserCourseStatus} on it.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param status         {@link UserCourseStatus}
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link CourseUser}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<CourseUser> findByUserIdAndStatus(long studentId,
                                           UserCourseStatus status,
                                           int start, int recordsPerPage)
            throws DaoException;

    /**
     * Method allows finding {@link by.alex.testing.domain.User}'s requests
     * on they {@link by.alex.testing.domain.Course}'s.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param userId         {@link by.alex.testing.domain.User} id
     * @return {@link List} of {@link CourseUser}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<CourseUser> findRequests(int start, int recordsPerPage, long userId)
            throws DaoException;

    /**
     * Method allows deleting {@link CourseUser} from DB.
     *
     * @param courseUser {@link CourseUser} that needs to be deleted
     * @return true if deleted, otherwise - false
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to delete entity
     */
    boolean delete(CourseUser courseUser) throws DaoException;

    /**
     * Method allows counting {@link CourseUser}'s in DB.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @return {@link Integer} quantity of {@link CourseUser}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer count(long courseId) throws DaoException;

    /**
     * Method allows counting {@link CourseUser}'s in DB by
     * {@link by.alex.testing.domain.User} name or login.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @param search   {@link by.alex.testing.domain.Course} title
     * @return {@link Integer} quantity of {@link CourseUser}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    Integer count(long courseId, String search) throws DaoException;

    /**
     * Method allows counting {@link by.alex.testing.domain.User}'s requests
     * on {@link by.alex.testing.domain.Course}'s created by teacher.
     *
     * @param teacherId {@link by.alex.testing.domain.User} id
     * @return {@link Integer} quantity of {@link CourseUser}'s requests
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    int countRequests(long teacherId) throws DaoException;

    /**
     * Method allows counting {@link by.alex.testing.domain.User}
     * {@link by.alex.testing.domain.Course}'s which they enter.
     *
     * @param studentId {@link by.alex.testing.domain.User} id
     * @param status    {@link UserCourseStatus}
     * @return {@link Integer} quantity of
     * {@link by.alex.testing.domain.Course}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to count entities
     */
    int countStudentCourses(long studentId, UserCourseStatus status)
            throws DaoException;
}
