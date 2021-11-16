package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.UserDao;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.User;
import by.alex.testing.service.HashService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.UserService;
import by.alex.testing.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private static final UserService instance = new UserServiceImpl();

    public static UserService getInstance() {
        return instance;
    }

    private UserServiceImpl() {
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        logger.info("Searching user by login: {}", login);
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        try {
            return userDao.readByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public List<String> register(User user) throws ServiceException {
        logger.info("Registering user");
        List<String> errors = UserValidator.validate(user);
        if (!errors.isEmpty()) {
            return errors;
        }
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        try {
            char[] password = user.getPassword();
            String hashedPsw = HashService.hash(password);
            user.setPassword(hashedPsw.toCharArray());
            userDao.create(user);
            return errors;
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        User user = null;
        try {
            User userByLogin = userDao.readByLogin(login);
            if (userByLogin != null &&
                    HashService.check(password, userByLogin.getPassword())) {
                user = userByLogin;
            }
            return user;
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

}
