package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.UserDao;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;

import java.sql.Connection;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        User user = null;

        try {
            user = userDao.readByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }

        return user;
    }

    @Override
    public List<User> readAllUsers() throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        List<User> users;
        try {
            users = userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public void removeUser(User user) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        try {
            userDao.delete(user.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

    @Override
    public void register(User user) throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        try {
            char[] password = user.getPassword();
            String hashedPsw =
                    SCryptUtil.scrypt(String.valueOf(password), 16, 16, 16);
            user.setPassword(hashedPsw.toCharArray());
            userDao.create(user);
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
                    SCryptUtil.check(password, String.valueOf(userByLogin.getPassword()))) {
                user = userByLogin;
            }
            return user;
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO layer: ", e);
        }
    }

}