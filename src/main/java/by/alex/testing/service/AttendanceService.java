package by.alex.testing.service;

import by.alex.testing.domain.Attendance;

public interface AttendanceService {

    /**
     * Method to find {@link Attendance} by id.
     *
     * @param id {@link Attendance} id
     * @return {@link Attendance}
     * @throws ServiceException if Dao layer provided exception
     */
    Attendance findAttendance(long id) throws ServiceException;
}
