package by.alex.testing.dao;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.CourseUser;

import java.util.List;

/**
 * Dao interface for {@link Attendance}.
 */
public interface AttendanceDao extends BaseDao<Attendance> {

    /**
     * @param lessonId {@link by.alex.testing.domain.Lesson} id
     * @return {@link List} of {@link Attendance}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Attendance> findByLessonId(long lessonId) throws DaoException;

    /**
     * @param lessonId  {@link by.alex.testing.domain.Lesson} id
     * @param studentId {@link by.alex.testing.domain.User} id
     * @return {@link Attendance}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    Attendance findByLessonAndStudentId(long lessonId, long studentId)
            throws DaoException;

    /**
     * @param courseUser {@link by.alex.testing.domain.CourseUser}
     * @return {@link List} of {@link Attendance}'s
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     *                      when tries to find entities
     */
    List<Attendance> findByCourseUser(CourseUser courseUser)
            throws DaoException;
}
