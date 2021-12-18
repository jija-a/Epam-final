package by.alex.testing.service;

public interface AdminService {

    /**
     * Method to delete {@link by.alex.testing.domain.User}.
     *
     * @param id {@link by.alex.testing.domain.User} id
     * @throws ServiceException if Dao layer provided exception
     */
    void deleteUser(long id) throws ServiceException;
}
