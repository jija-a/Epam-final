package by.alex.testing.service;

import by.alex.testing.domain.CourseCategory;

import java.util.List;

public interface CourseCategoryService {

    /**
     * Method to validate and then create {@link CourseCategory}.
     *
     * @param category {@link CourseCategory} that needs to be created
     * @return {@link List} of errors if not validated,
     * empty if validated successfully
     * @throws ServiceException if Dao layer provided exception
     */
    List<String> create(CourseCategory category) throws ServiceException;

    /**
     * Method to find all {@link CourseCategory}'s.
     *
     * @return {@link List} of {@link CourseCategory}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<CourseCategory> findAllCategories() throws ServiceException;

    /**
     * Method to find all {@link CourseCategory}'s.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link CourseCategory}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<CourseCategory> findAllCategories(int start, int recordsPerPage)
            throws ServiceException;

    /**
     * Method to find all {@link CourseCategory}'s by search request.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param search         search request
     * @return {@link List} of {@link CourseCategory}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<CourseCategory> findAllCategories(int start, int recordsPerPage,
                                           String search)
            throws ServiceException;

    /**
     * Method to find {@link CourseCategory} by id.
     *
     * @param id {@link CourseCategory} id
     * @return {@link CourseCategory}
     * @throws ServiceException if Dao layer provided exception
     */
    CourseCategory findCategory(long id)
            throws ServiceException;

    /**
     * Method to validate and then update {@link CourseCategory}.
     *
     * @param category {@link CourseCategory} that needs to be updated
     * @return {@link List} of errors if not validated,
     * empty if validated successfully
     * @throws ServiceException if Dao layer provided exception
     */
    List<String> updateCategory(CourseCategory category)
            throws ServiceException;

    /**
     * Method to delete {@link CourseCategory}.
     *
     * @param id {@link CourseCategory} id
     * @throws ServiceException if Dao layer provided exception
     */
    void deleteCategory(long id) throws ServiceException;

    /**
     * Method to count all {@link CourseCategory}'s.
     *
     * @return {@link Integer} {@link CourseCategory}'s quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countCategories() throws ServiceException;

    /**
     * Method to find all {@link CourseCategory}'s by search request.
     *
     * @param search search request
     * @return {@link Integer} {@link CourseCategory}'s quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countCategories(String search) throws ServiceException;
}
