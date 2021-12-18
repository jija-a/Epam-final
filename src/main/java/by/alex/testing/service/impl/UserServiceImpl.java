package by.alex.testing.service.impl;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.User;
import by.alex.testing.service.HashService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.UserService;
import by.alex.testing.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class UserServiceImpl implements UserService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * {@link UserServiceImpl} instance. Singleton pattern.
     */
    private static final UserService SERVICE = new UserServiceImpl();

    /**
     * @return {@link UserServiceImpl} instance.
     */
    public static UserService getInstance() {
        return SERVICE;
    }

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    private UserServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = factory.getUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public List<User> findAllUsers(final int start, final int recordsPerPage)
            throws ServiceException {
        LOGGER.info("Searching all users, start: {}, rec: {}",
                start, recordsPerPage);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.findAll(start, recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public User findUser(final long id) throws ServiceException {
        LOGGER.info("Searching user by id: {}", id);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public List<User> findAllUsers(final int start,
                                   final int recordsPerPage,
                                   final String search)
            throws ServiceException {
        LOGGER.info("Searching user by name, start: {}, rec: {}, search: {}",
                start, recordsPerPage, search);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.findBySearchRequest(start, recordsPerPage, search);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public User findUserByLogin(final String login) throws ServiceException {
        LOGGER.info("Searching user by login: {}", login);
        try {
            handler.beginNoTransaction(userDao);
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }

    @Override
    public boolean updateUserProfile(final User user) throws ServiceException {

        LOGGER.info("Updating user profile, user id: {}", user.getId());
        boolean isUpdated;
        try {
            handler.begin(userDao);
            isUpdated = userDao.update(user);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
        return isUpdated;
    }

    @Override
    public Integer countAllUsers() throws ServiceException {
        LOGGER.info("Counting all users");
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
    public Integer countAllUsers(final String search) throws ServiceException {
        LOGGER.info("Counting all users by search request: {}", search);

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
    public List<String> register(final User user) throws ServiceException {
        LOGGER.debug("Registering user, login: {}", user.getLogin());
        List<String> errors = new ArrayList<>();
        if (this.findUserByLogin(user.getLogin()) == null) {
            errors = UserValidator.validate(user);
            if (errors.isEmpty()) {
                try {
                    handler.begin(userDao);
                    char[] password = user.getPassword();
                    String hashedPsw = HashService.hash(password);
                    user.setPassword(hashedPsw.toCharArray());
                    userDao.save(user);
                    handler.commit();
                } catch (DaoException e) {
                    handler.rollback();
                    throw new ServiceException(e.getMessage(), e);
                } finally {
                    handler.endNoTransaction();
                }
            }
        } else {
            String msg = MessageManager.INSTANCE
                    .getMessage(MessageConstant.LOGIN_IS_TAKEN_ERROR);
            errors.add(msg);
        }
        return errors;
    }

    @Override
    public User login(final String login, final String password)
            throws ServiceException {
        LOGGER.info("Logging In user, login: {}", login);
        try {
            handler.beginNoTransaction(userDao);
            User userByLogin = userDao.findByLogin(login);
            if (userByLogin != null
                    && HashService.check(password, userByLogin.getPassword())) {
                return userByLogin;
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }
}
