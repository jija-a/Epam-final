package by.alex.testing.dao;

import by.alex.testing.domain.Lesson;

import java.util.List;

/**
 * Dao interface for {@link Lesson}.
 */
public interface LessonDao extends BaseDao<Lesson> {

    /**
     * Method allows finding {@link Lesson}'s from start to
     * record per page
     * by {@link by.alex.testing.domain.Course} id.
     *
     * @param courseId       {@link by.alex.testing.domain.Course} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Lesson}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Lesson> findByCourseId(long courseId, int start, int recordsPerPage)
            throws DaoException;

    /**
     * Method allows finding {@link Lesson}'s from start to
     * record per page by {@link by.alex.testing.domain.Course}
     * and {@link by.alex.testing.domain.User} id.
     *
     * @param courseId       {@link by.alex.testing.domain.Course} id
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Lesson}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Lesson> findByCourseAndStudentId(long courseId, long studentId,
                                          int start, int recordsPerPage)
            throws DaoException;

    /**
     * Method allows counting all {@link Lesson}'s on concrete
     * {@link by.alex.testing.domain.Course} by course id.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @return {@link Integer} {@link Lesson}'s quantity
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    int count(long courseId) throws DaoException;

    /**
     * Method allows counting all {@link Lesson}'s
     * on which student be on list.
     *
     * @param courseId  {@link by.alex.testing.domain.Course} id
     * @param studentId {@link by.alex.testing.domain.User} id
     * @return {@link Integer} {@link Lesson}'s quantity
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    int count(long courseId, long studentId) throws DaoException;
}
