package by.alex.testing.service;

import by.alex.testing.domain.Course;

import java.util.List;

public interface CourseService {

    List<Course> readAllCourses(int start, int recOnPage) throws ServiceException;

    Course readCourseById(long courseId) throws ServiceException;

    List<Course> readCourseByTitle(int start, int recOnPage, String title) throws ServiceException;

    Integer countAllCourses() throws ServiceException;

    Integer countAllCourses(String search) throws ServiceException;

    void deleteCourse(long courseId) throws ServiceException;
}
