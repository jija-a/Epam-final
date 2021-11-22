package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.domain.User;
import by.alex.testing.service.HashService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.UserService;
import by.alex.testing.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private static final UserService instance = new UserServiceImpl();

    public static UserService getInstance() {
        return instance;
    }

    private final TransactionHandler handler;
    private final UserDaoImpl userDao;

    private UserServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = factory.getUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        logger.info("Searching user by login: {}", login);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> register(User user) throws ServiceException {
        logger.debug("Registering user");
        List<String> errors = UserValidator.validate(user);

        if (errors.isEmpty()) {
            try {
                handler.beginNoTransaction(userDao);

                char[] password = user.getPassword();
                String hashedPsw = HashService.hash(password);
                user.setPassword(hashedPsw.toCharArray());
                userDao.create(user);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage(), e);
            } finally {
                handler.endNoTransaction();
            }
        }
        return errors;
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        try {
            handler.beginNoTransaction(userDao);
            User userByLogin = userDao.readByLogin(login);
            if (userByLogin != null &&
                    HashService.check(password, userByLogin.getPassword())) {
                return userByLogin;
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public User findUserById(Long id) throws ServiceException {
        logger.info("Searching user by id: {}", id);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<String> updateUserProfile(User user) throws ServiceException {
        List<String> errors = UserValidator.validate(user);
        if (errors.isEmpty()) {
            logger.info("Reading users");
            try {
                handler.begin(userDao);
                userDao.update(user);
                handler.commit();
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage(), e);
            } finally {
                handler.end();
            }
        }
        return errors;
    }

    @Override
    public List<User> findAllUsers(int start, int total) throws ServiceException {
        logger.info("Reading users");
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readAll(start, total);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllUsers() throws ServiceException {
        logger.info("Counting users");
        try {
            handler.beginNoTransaction(userDao);
            return userDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<User> findUsersByName(int start, int total, String search) throws ServiceException {
        logger.info("Reading users");
        try {
            handler.beginNoTransaction(userDao);
            return userDao.readByName(start, total, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public Integer countAllUsers(String search) throws ServiceException {
        logger.info("Counting users");
        try {
            handler.beginNoTransaction(userDao);
            return userDao.count(search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public void deleteUser(long userId) throws ServiceException {
        logger.info("Counting users");
        try {
            handler.begin(userDao);
            userDao.delete(userId);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }

}
