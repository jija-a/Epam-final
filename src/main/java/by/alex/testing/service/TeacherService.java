package by.alex.testing.service;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseUser;
import by.alex.testing.domain.Lesson;
import by.alex.testing.domain.User;

import java.util.List;

public interface TeacherService {

    /**
     * Method validates and then update {@link Course} in DB.
     *
     * @param course  {@link Course} that needs to be updated
     * @param teacher User that call this method
     * @return {@link List} of errors if {@link Course}
     * does not pass validation, empty if passed
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    List<String> updateCourse(Course course, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method to delete {@link Course} from DB.
     *
     * @param courseId {@link Course} that needs to be deleted.
     * @param teacher  User that call this method
     * @return true if deleted or false if not
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    boolean deleteCourse(long courseId, User teacher)
            throws ServiceException, AccessException;

    /**
     * Finds all {@link Course} {@link Lesson}'s.
     *
     * @param courseId       {@link Course} id
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param teacher        User that call this method
     * @return {@link List} of {@link Lesson}'s
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    List<Lesson> findAllLessons(long courseId, int start,
                                int recordsPerPage, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method validates and then update {@link Lesson} in DB.
     *
     * @param lesson  {@link Lesson} needs to be updated
     * @param teacher User that call this method
     * @return {@link List} of errors if {@link Lesson} doesn't pass validation,
     * empty if passed
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    List<String> updateLesson(Lesson lesson, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method deletes {@link Lesson} from DB.
     *
     * @param lessonId {@link Lesson} id
     * @param courseId {@link Course} id
     * @param teacher  User that call this method
     * @return true if deleted or else false
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    boolean deleteLesson(long lessonId, long courseId, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method finds all {@link Lesson} {@link Attendance}'s.
     *
     * @param lessonId {@link Lesson} id
     * @param courseId {@link Course} id
     * @param teacher  User that call this method
     * @return {@link List} of {@link Attendance}'s
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    List<Attendance> findAllAttendances(long lessonId, long courseId,
                                        User teacher)
            throws ServiceException, AccessException;

    /**
     * Method validate and then update {@link Attendance} in DB.
     *
     * @param attendance {@link Attendance} that needs to be updated
     * @param courseId   {@link Course} id
     * @param teacher    User that call this method
     * @return {@link List} of errors if {@link Attendance} doesn't
     * pass validation, empty if passed
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    List<String> updateAttendance(Attendance attendance, long courseId,
                                  User teacher)
            throws ServiceException, AccessException;

    /**
     * Method to find {@link CourseUser}'s.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param courseId       {@link Course} id
     * @param teacher        User that call this method
     * @return {@link List} of {@link CourseUser}'s
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    List<CourseUser> findCourseUsers(int start, int recordsPerPage,
                                     long courseId, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method validate and then update {@link Attendance} in DB.
     *
     * @param courseUser {@link CourseUser} that needs to be updated
     * @param teacher    User that call this method
     * @return {@link List} of errors if {@link CourseUser} doesn't
     * pass validation, empty if passed
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    boolean updateCourseUser(CourseUser courseUser, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method to decline {@link CourseUser} request on {@link Course}.
     *
     * @param courseUser {@link CourseUser} which request should be declined
     * @param teacher    User that call this method
     * @return true if successfully or else false
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    boolean declineRequest(CourseUser courseUser, User teacher)
            throws ServiceException, AccessException;

    /**
     * Method to delete {@link CourseUser} from {@link Course}.
     *
     * @param courseUser {@link CourseUser} that needs to be deleted
     * @param teacher    User that call this method
     * @return true if successfully or else false
     * @throws ServiceException      if dao layer provided exception
     * @throws AccessException if teacher does not have access
     *                               to manipulate course
     */
    boolean deleteUserFromCourse(CourseUser courseUser, User teacher)
            throws ServiceException, AccessException;
}
