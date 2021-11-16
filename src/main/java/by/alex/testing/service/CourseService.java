package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.Quiz;

import java.util.List;

public interface CourseService {

    List<Course> readCourseByTitle(String title) throws ServiceException;

    List<Quiz> readAllTestsByCourseName(long courseId) throws ServiceException;

    void removeTestFromCourse(long testId) throws ServiceException;

    Course readCourseById(long courseId) throws ServiceException;

    void updateCourseInfo(Course course) throws ServiceException;

    List<CourseCategory> readAllCourseCategories() throws ServiceException;

    List<Course> readUserCourses(Long userId) throws ServiceException;

    List<Course> readTeacherCourses(Long userId) throws ServiceException;
}
