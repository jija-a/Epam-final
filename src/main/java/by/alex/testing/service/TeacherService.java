package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;

import java.util.List;

public interface TeacherService {

    List<Course> readAllCourses(int start, int recOnPage, long userId) throws ServiceException;

    Integer countAllCourses(long userId) throws ServiceException;

    void deleteUserFromCourse(CourseUser courseUser)
            throws ServiceException;

    List<String> createCourse(Course course) throws ServiceException;

    List<String> updateCourse(Course course) throws ServiceException;
}
