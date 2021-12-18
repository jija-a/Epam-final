package by.alex.testing.service;

import by.alex.testing.domain.User;

import java.util.List;

public interface UserService {

    /**
     * Method to find all {@link User}'s.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @return {@link List} of {@link User}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<User> findAllUsers(int start, int recordsPerPage)
            throws ServiceException;

    /**
     * Method to find {@link User} by id.
     *
     * @param id {@link User} id
     * @return {@link User}
     * @throws ServiceException if Dao layer provided exception
     */
    User findUser(long id) throws ServiceException;

    /**
     * Method to find {@link User}'s by search request.
     *
     * @param start          start number in DB
     * @param recordsPerPage how many entities needs to be founded
     * @param search         {@link String} search request
     * @return {@link List} of {@link User}'s
     * @throws ServiceException if Dao layer provided exception
     */
    List<User> findAllUsers(int start, int recordsPerPage, String search)
            throws ServiceException;

    /**
     * Method to find {@link User} by login.
     *
     * @param login {@link User} login
     * @return {@link User}
     * @throws ServiceException if Dao layer provided exception
     */
    User findUserByLogin(String login) throws ServiceException;

    /**
     * Method to validate and then update {@link User} profile data.
     *
     * @param user {@link User} that needs to be updated
     * @return true if successfully, else - false
     * @throws ServiceException if Dao layer provided exception
     */
    boolean updateUserProfile(User user) throws ServiceException;

    /**
     * Method to count all {@link User}'s.
     *
     * @return {@link Integer} {@link User} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllUsers() throws ServiceException;

    /**
     * Method to count all {@link User}'s by search request.
     *
     * @param search search request
     * @return {@link Integer} {@link User} quantity
     * @throws ServiceException if Dao layer provided exception
     */
    Integer countAllUsers(String search) throws ServiceException;

    /**
     * Method to register {@link User}.
     *
     * @param user {@link User} that needs to be registered
     * @return {@link List} of errors if not validated,
     * empty if successfully registered
     * @throws ServiceException if Dao layer provided exception
     */
    List<String> register(User user) throws ServiceException;

    /**
     * Method to {@link User} enter the system.
     *
     * @param login    {@link User} login
     * @param password {@link User} password
     * @return {@link User} if successful, else null
     * @throws ServiceException if Dao layer provided exception
     */
    User login(String login, String password) throws ServiceException;
}
