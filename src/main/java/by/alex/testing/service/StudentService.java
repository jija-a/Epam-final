package by.alex.testing.service;

import by.alex.testing.domain.*;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<CourseUser> readStudentCourses(long studentId, int start, int recordsPerPage) throws ServiceException;

    List<Course> readAvailableCourses(long studentId, int start, int recordsPerPage) throws ServiceException;

    List<Course> readAvailableCourses(long studentId, int start, int recordsPerPage, String search) throws ServiceException;

    List<Course> readStudentCoursesByStatus(long studentId, UserCourseStatus status) throws ServiceException;

    List<Course> readStudentCoursesByStatus(long studentId, UserCourseStatus status, int start, int recordsPerPage) throws ServiceException;

    Map<Lesson, Attendance> findAllLessons(long courseId, long studentId, int start, int recordsPerPage) throws ServiceException;

    int countStudentLessons(long courseId, long studentId) throws ServiceException;

    int countStudentCourses(long studentId) throws ServiceException;

    Integer countAvailableCourses(long studentId) throws ServiceException;

    Integer countAvailableCourses(long studentId, String search) throws ServiceException;

    boolean signOnCourse(CourseUser courseUser) throws ServiceException;

    boolean leaveCourse(CourseUser courseUser) throws ServiceException;
}
