package by.alex.testing.service;

import by.alex.testing.domain.CourseCategory;

import java.util.List;

public interface CourseCategoryService {

    List<CourseCategory> readAllCourseCategories() throws ServiceException;

    List<CourseCategory> readAllCourseCategories(int start, int recOnPage) throws ServiceException;

    CourseCategory readCategoryById(long categoryId) throws ServiceException;

    List<CourseCategory> readCourseCategoriesByTitle(int start, int recOnPage, String search) throws ServiceException;

    void deleteCourseCategory(long parseLong) throws ServiceException;

    Integer countAllCourseCategories() throws ServiceException;

    Integer countAllCourseCategories(String search) throws ServiceException;

    List<String> updateCategory(CourseCategory category) throws ServiceException;

    List<String> create(CourseCategory category) throws ServiceException;
}
