package by.alex.testing.dao;

import by.alex.testing.domain.Course;

import java.util.List;

public interface CourseDao extends Dao<Course, Long> {

    List<Course> readCourseByTitle(String title) throws DaoException;
}
