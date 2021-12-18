package by.alex.testing.service;

import by.alex.testing.domain.Lesson;

import java.util.List;

public interface LessonService {

    /**
     * Method to find {@link Lesson} by it id.
     *
     * @param lessonId {@link Lesson} id
     * @return {@link Lesson}
     * @throws ServiceException if Dao layer provided exception
     */
    Lesson findLessonById(long lessonId) throws ServiceException;

    /**
     * Method to validate and then create {@link Lesson}.
     *
     * @param lesson {@link Lesson} that needs to be created
     * @return {@link List} of errors if not validated,
     * empty if successful
     * @throws ServiceException if Dao layer provided exception
     */
    List<String> createLesson(Lesson lesson) throws ServiceException;

    /**
     * Method to count {@link Lesson} on course.
     *
     * @param courseId {@link by.alex.testing.domain.Course} id
     * @return {@link Integer} {@link Lesson} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    int countAllLessons(long courseId) throws ServiceException;

    /**
     * Method to count {@link by.alex.testing.domain.User} {@link Lesson}'s.
     *
     * @param courseId  {@link by.alex.testing.domain.Course} id
     * @param studentId {@link by.alex.testing.domain.User} id
     * @return {@link Integer} {@link Lesson} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    int countStudentLessons(long courseId, long studentId)
            throws ServiceException;
}
