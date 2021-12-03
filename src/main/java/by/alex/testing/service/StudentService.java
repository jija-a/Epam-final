package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;

import java.util.List;

public interface StudentService {

    List<Course> readStudentCourses(long studentId) throws ServiceException;

    List<Course> readAvailableCourses(long studentId) throws ServiceException;

    void signOnCourse(CourseUser courseUser) throws ServiceException;
}
