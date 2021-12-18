package by.alex.testing.service.impl;

import by.alex.testing.dao.AttendanceDao;
import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.DaoFactory;
import by.alex.testing.dao.TransactionHandler;
import by.alex.testing.dao.UserDao;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.User;
import by.alex.testing.service.AttendanceService;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AttendanceServiceImpl implements AttendanceService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AttendanceServiceImpl.class);

    /**
     * {@link AttendanceServiceImpl} instance. Singleton pattern.
     */
    private static final AttendanceService SERVICE =
            new AttendanceServiceImpl();

    /**
     * @return {@link AttendanceServiceImpl} instance
     */
    public static AttendanceService getInstance() {
        return SERVICE;
    }

    /**
     * @see AttendanceDao
     */
    private final AttendanceDao attendanceDao;

    /**
     * @see UserDao
     */
    private final UserDao userDao;

    /**
     * @see TransactionHandler
     */
    private final TransactionHandler handler;

    private AttendanceServiceImpl() {
        DaoFactory daoFactory =
                DaoFactory.getDaoFactory(DaoFactory.DaoType.MYSQL);
        this.attendanceDao = daoFactory.getAttendanceDao();
        this.userDao = daoFactory.getUserDao();
        this.handler = new TransactionHandler();
    }

    @Override
    public Attendance findAttendance(final long id)
            throws ServiceException {
        LOGGER.info("Searching attendance by id: {}", id);

        try {
            handler.beginNoTransaction(attendanceDao, userDao);
            Attendance attendance = attendanceDao.findOne(id);
            long studentId = attendance.getStudent().getId();
            User student = userDao.findOne(studentId);
            attendance.setStudent(student);
            return attendance;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            handler.endNoTransaction();
        }
    }
}
