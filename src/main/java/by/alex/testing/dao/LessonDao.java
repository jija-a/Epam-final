package by.alex.testing.dao;

import by.alex.testing.domain.Lesson;

import java.util.List;

public interface LessonDao extends BaseDao<Lesson> {
    List<Lesson> readByCourseId(long courseId, int start, int recordsPerPage) throws DaoException;

    List<Lesson> readByCourseAndStudentId(long courseId, long studentId, int start, int recordsPerPage) throws DaoException;

    int count(long courseId) throws DaoException;

    int count(long courseId, long studentId) throws DaoException;
}
