package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.Quiz;

import java.util.List;

public interface CourseService {

    List<Course> readCourseByTitle(int start, int recOnPage, String title) throws ServiceException;

    List<Quiz> readAllTestsByCourseName(long courseId) throws ServiceException;

    void removeTestFromCourse(long testId) throws ServiceException;

    Course readCourseById(long courseId) throws ServiceException;

    void updateCourseInfo(Course course) throws ServiceException;

    List<CourseCategory> readAllCourseCategories(int start, int recOnPage) throws ServiceException;

    List<Course> readUserCourses(Long userId) throws ServiceException;

    List<Course> readTeacherCourses(Long userId) throws ServiceException;

    Integer countAllCourses(String search) throws ServiceException;

    Integer countAllCourses() throws ServiceException;

    List<Course> readAllCourses(int start, int recOnPage) throws ServiceException;

    void deleteCourse(long courseId) throws ServiceException;

    List<CourseCategory> readCourseCategoriesByTitle(int start, int recOnPage, String search) throws ServiceException;

    Integer countAllCourseCategories(String search) throws ServiceException;

    Integer countAllCourseCategories() throws ServiceException;

    void deleteCourseCategory(long parseLong) throws ServiceException;
}
