package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;

import java.util.List;

public interface CourseService {

    /**
     * Method to read all {@link Course}'s.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link Course}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> findAllCourses(int start, int recordsPerPage)
            throws ServiceException;

    /**
     * Method to find {@link Course} by title.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param title          {@link Course} title
     * @return {@link List} of {@link Course}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> findCoursesByTitle(int start, int recordsPerPage, String title)
            throws ServiceException;

    /**
     * Method to find {@link Course} by id.
     *
     * @param id {@link Course} id
     * @return {@link Course} if found, else null
     * @throws ServiceException if Dao layer provided exception
     */
    Course findCourse(long id) throws ServiceException;

    /**
     * Method to find all {@link Course}'s by owner id.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param teacherId      {@link by.alex.testing.domain.User}
     *                       that called method
     * @return {@link List} of {@link Course}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<Course> findAllCoursesByTeacherId(int start, int recordsPerPage,
                                           long teacherId)
            throws ServiceException;

    /**
     * Method to validate and then create {@link Course}.
     *
     * @param course {@link Course} that needs to be created
     * @return {@link List} of errors if not validated,
     * empty if validated successfully
     * @throws ServiceException if Dao layer provided exception
     */
    List<String> createCourse(Course course) throws ServiceException;

    /**
     * Method to count all {@link Course}'s.
     *
     * @return {@link Integer} {@link Course} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllCourses() throws ServiceException;

    /**
     * Method to count all {@link Course}'s by search request.
     *
     * @param search search request
     * @return {@link Integer} {@link Course} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllCourses(String search) throws ServiceException;

    /**
     * Method to count all students requests on teacher course.
     *
     * @param teacherId owner id
     * @return {@link Integer} requests quantity
     * @throws ServiceException if Dao layer provided exception
     */
    int countAllRequests(long teacherId) throws ServiceException;

    /**
     * Method to count all student courses.
     *
     * @param id {@link by.alex.testing.domain.User} id
     * @return {@link Integer} student courses quantity
     * @throws ServiceException if Dao layer provided exception
     */
    int countStudentCourses(long id) throws ServiceException;

    /**
     * Method to count {@link Course}'s by owner id.
     *
     * @param id owner id
     * @return {@link Integer} {@link Course}'s qty
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllCoursesByTeacherId(long id) throws ServiceException;

    /**
     * Method to find all students requests on teacher course.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param teacherId      owner id
     * @return {@link List} of {@link CourseUser}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<CourseUser> findRequestsOnCourse(int start, int recordsPerPage,
                                          long teacherId)
            throws ServiceException;
}
