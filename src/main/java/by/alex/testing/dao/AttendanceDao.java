package by.alex.testing.dao;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.CourseUser;

import java.util.List;

public interface AttendanceDao extends BaseDao<Attendance> {

    List<Attendance> readByLessonId(long lessonId) throws DaoException;

    Attendance readByLessonAndStudentId(long lessonId, long studentId) throws DaoException;

    List<Attendance> readByCourseUser(CourseUser courseUser) throws DaoException;
}
