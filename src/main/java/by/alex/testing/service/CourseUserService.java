package by.alex.testing.service;

import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.User;

import java.util.List;

public interface CourseUserService {

    /**
     * Method to find {@link CourseUser}'s by
     * {@link by.alex.testing.domain.Course} id.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @return {@link List} of {@link CourseUser}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<User> findCourseUsers(long courseId) throws ServiceException;

    /**
     * Method to find {@link CourseUser}'s by
     * {@link CourseUser} name.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param courseId       {@link by.alex.testing.domain.Course} id
     * @param search         {@link String} search request
     * @param teacher        {@link User} that called method
     * @return {@link List} of {@link CourseUser}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<CourseUser> findCourseUsersByName(int start, int recordsPerPage,
                                           long courseId, String search,
                                           User teacher)
            throws ServiceException;

    /**
     * Method to find {@link CourseUser} by it
     * {@link by.alex.testing.domain.Course} id and {@link User} id.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @param userId   {@link User} id
     * @return {@link CourseUser}
     * @throws ServiceException if Dao layer provided exception
     */
    CourseUser findCourseUser(long courseId, long userId)
            throws ServiceException;

    /**
     * Method to count {@link CourseUser}'s by
     * {@link by.alex.testing.domain.Course} id.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @return {@link CourseUser} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllCourseUsers(long courseId) throws ServiceException;

    /**
     * Method to count {@link CourseUser}'s by
     * {@link by.alex.testing.domain.Course} id and search request.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @param search   {@link String} search request
     * @return {@link CourseUser} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllCourseUsers(long courseId, String search)
            throws ServiceException;
}
