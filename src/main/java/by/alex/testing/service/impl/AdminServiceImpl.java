package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.service.AdminService;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminServiceImpl implements AdminService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AdminServiceImpl.class);

    /**
     * {@link AdminServiceImpl} instance. Singleton pattern.
     */
    private static final AdminService SERVICE = new AdminServiceImpl();

    /**
     * @return {@link AdminServiceImpl} instance
     */
    public static AdminService getInstance() {
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

    private AdminServiceImpl() {
        DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.userDao = factory.getUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public void deleteUser(final long id) throws ServiceException {
        LOGGER.info("Deleting user, id: {}", id);

        try {
            handler.begin(userDao);
            userDao.delete(id);
            handler.commit();
        } catch (DaoException e) {
            handler.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.end();
        }
    }
}
