package by.alex.testing.service;

import by.alex.testing.domain.*;

import java.util.List;

public interface TeacherService {

    List<Course> findAllCoursesByTeacherId(int start, int recOnPage, long teacherId)
            throws ServiceException;
    List<String> createCourse(Course course) throws ServiceException;
    List<String> updateCourse(Course course, User teacher) throws ServiceException, CourseAccessDeniedException;
    boolean deleteCourse(long courseId, User teacher) throws ServiceException, CourseAccessDeniedException;
    Integer countAllCoursesByTeacherId(long teacherId) throws ServiceException;

    List<Lesson> findAllLessons(long courseId, int start, int recordsPerPage, User teacher) throws ServiceException, CourseAccessDeniedException;
    Lesson findLessonById(long lessonId) throws ServiceException;
    List<String> createLesson(Lesson lesson) throws ServiceException;
    List<String> updateLesson(Lesson lesson, User teacher) throws ServiceException, CourseAccessDeniedException;
    boolean deleteLesson(long lessonId, User teacher) throws ServiceException, CourseAccessDeniedException;
    int countAllLessons(long courseId) throws ServiceException;

    List<Attendance> findAllAttendances(long lessonId, User teacher)
            throws ServiceException, CourseAccessDeniedException;
    Attendance findAttendance(long attendanceId) throws ServiceException;
    List<String> updateAttendance(Attendance attendance, User teacher)
            throws ServiceException, CourseAccessDeniedException;

    List<CourseUser> findRequestsOnCourse(int start, int recOnPage, long teacherId)
            throws ServiceException;
    int countAllRequests(long teacherId) throws ServiceException;

    List<User> findCourseUsers(long courseId) throws ServiceException;
    List<CourseUser> findCourseUsers(int start, int recOnPage, long courseI, User teacher)
            throws ServiceException, CourseAccessDeniedException;
    List<CourseUser> findCourseUsersByName(int start, int recOnPage, long courseId, String search, User teacher)
            throws ServiceException;
    CourseUser findCourseUser(long courseId, long userId) throws ServiceException;
    boolean updateCourseUser(CourseUser courseUser, User user) throws ServiceException, CourseAccessDeniedException;
    boolean deleteCourseUser(CourseUser courseUser, User user) throws ServiceException, CourseAccessDeniedException;
    boolean deleteUserFromCourse(CourseUser courseUser, User teacher)
            throws ServiceException, CourseAccessDeniedException;
    Integer countAllCourseUsers(long courseId) throws ServiceException;
    Integer countAllCourseUsers(long courseId, String search) throws ServiceException;
}
