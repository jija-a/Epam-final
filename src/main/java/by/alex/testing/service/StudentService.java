package by.alex.testing.service;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.UserCourseStatus;

import java.util.List;
import java.util.Map;

public interface StudentService {

    /**
     * Method to read {@link by.alex.testing.domain.User} {@link Course}'s.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link CourseUser}
     * @throws ServiceException if Dao layer provided exception
     */
    List<CourseUser> readStudentCourses(long studentId, int start,
                                        int recordsPerPage)
            throws ServiceException;

    /**
     * Method to read {@link by.alex.testing.domain.User}
     * {@link Course}'s by {@link UserCourseStatus}.
     *
     * @param studentId {@link by.alex.testing.domain.User} id
     * @param status    {@link UserCourseStatus}
     * @return {@link List} of {@link Course}
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> readStudentCourses(long studentId, UserCourseStatus status)
            throws ServiceException;

    /**
     * Method to read {@link by.alex.testing.domain.User}
     * {@link Course}'s by {@link UserCourseStatus}.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param status         {@link UserCourseStatus}
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> readStudentCourses(long studentId, UserCourseStatus status,
                                    int start, int recordsPerPage)
            throws ServiceException;

    /**
     * Method to read {@link Course}'s that
     * {@link by.alex.testing.domain.User} doesn't enter yet.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> readAvailableCourses(long studentId, int start,
                                      int recordsPerPage)
            throws ServiceException;

    /**
     * Method to read {@link Course}'s by search request
     * that {@link by.alex.testing.domain.User} doesn't enter yet.
     *
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param search         {@link String} search request
     *                       from {@link by.alex.testing.domain.User}
     * @return {@link List} of {@link Course}
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> readAvailableCourses(long studentId, int start,
                                      int recordsPerPage, String search)
            throws ServiceException;

    /**
     * Method to find {@link by.alex.testing.domain.User}
     * {@link Lesson}'s and it {@link Attendance}.
     *
     * @param courseId       {@link Course} id
     * @param studentId      {@link by.alex.testing.domain.User} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link Map} of {@link Lesson} as a key
     * and {@link Attendance} as a value
     * @throws ServiceException if Dao layer provided exception
     */
    Map<Lesson, Attendance> findLessons(long courseId, long studentId,
                                        int start, int recordsPerPage)
            throws ServiceException;

    /**
     * Method to count {@link Course}'s that
     * {@link by.alex.testing.domain.User} doesn't enter.
     *
     * @param studentId {@link by.alex.testing.domain.User} id
     * @return {@link Integer} number of entities
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAvailableCourses(long studentId) throws ServiceException;

    /**
     * Method to count {@link Course}'s with search request
     * that {@link by.alex.testing.domain.User} doesn't enter.
     *
     * @param studentId {@link by.alex.testing.domain.User} id
     * @param search    {@link String} search request
     * @return {@link Integer} number of entities
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAvailableCourses(long studentId, String search)
            throws ServiceException;

    /**
     * Method to sign {@link by.alex.testing.domain.User} on {@link Course}.
     *
     * @param courseUser {@link CourseUser} that need to be added
     * @return {@link Boolean} true if signed or else false
     * @throws ServiceException if Dao layer provided exception
     */
    boolean signOnCourse(CourseUser courseUser) throws ServiceException;

    /**
     * Method to leave {@link Course}.
     *
     * @param courseUser {@link CourseUser} that need to be deleted
     * @return {@link Boolean} true successfully leave or else false
     * @throws ServiceException if Dao layer provided exception
     */
    boolean leaveCourse(CourseUser courseUser) throws ServiceException;
}
