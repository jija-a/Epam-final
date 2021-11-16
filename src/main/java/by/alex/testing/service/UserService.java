package by.alex.testing.service;

import by.alex.testing.domain.User;

import java.util.List;

public interface UserService {

    User findUserByLogin(String login) throws ServiceException;

    List<String> register(User user) throws ServiceException;

    User login(String login, String password) throws ServiceException;
}
