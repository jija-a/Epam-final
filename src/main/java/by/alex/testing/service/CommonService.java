package by.alex.testing.service;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.domain.User;

import java.util.List;

public interface CommonService {

    List<User> findAllUsers(int start, int total) throws ServiceException;

    User findUserById(Long id) throws ServiceException;

    List<User> findUsersByName(int start, int paginationLimit, String search) throws ServiceException;

    User findUserByLogin(String login) throws ServiceException;

    boolean updateUserProfile(User user) throws ServiceException;

    Integer countAllUsers() throws ServiceException;

    Integer countAllUsers(String search) throws ServiceException;

    List<String> register(User user) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    List<CourseCategory> readAllCourseCategories() throws ServiceException;

    Integer countAllCourses(String search) throws ServiceException;

    Integer countAllCourses() throws ServiceException;

    List<Course> readAllCourses(int start, int recOnPage) throws ServiceException;

    Course readCourseById(long courseId) throws ServiceException;

    List<Course> readCourseByTitle(int start, int recOnPage, String title) throws ServiceException;
}
