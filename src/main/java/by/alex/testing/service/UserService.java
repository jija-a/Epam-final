package by.alex.testing.service;

import by.alex.testing.domain.User;

import java.util.List;

public interface UserService {

    User findUserByLogin(String login) throws ServiceException;

    List<String> register(User user) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    User findUserById(Long id) throws ServiceException;

    List<String> updateUserProfile(User user) throws ServiceException;

    List<User> findAllUsers(int start, int total) throws ServiceException;

    Integer countAllUsers() throws ServiceException;

    List<User> findUsersByName(int start, int paginationLimit, String search) throws ServiceException;

    Integer countAllUsers(String search) throws ServiceException;

    void deleteUser(long userId) throws ServiceException;
}
