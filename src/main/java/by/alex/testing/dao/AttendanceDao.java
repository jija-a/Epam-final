package by.alex.testing.dao;

import by.alex.testing.domain.Attendance;

import java.util.List;

public interface AttendanceDao {

    List<Attendance> readByLessonId(long lessonId) throws DaoException;
}
