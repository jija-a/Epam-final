package by.alex.testing.service;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;

import java.util.List;

public final class AttendanceCalculator {

    /**
     * Constant to calculate percents.
     */
    private static final double HUNDRED_PERCENT = 100;

    private AttendanceCalculator() {
    }

    /**
     * Method to calculate {@link by.alex.testing.domain.User}
     * {@link by.alex.testing.domain.Course} rating.
     *
     * @param att {@link List} of
     *            {@link by.alex.testing.domain.User}
     *            {@link Attendance}'s
     * @return {@link Double} {@link by.alex.testing.domain.User} rating
     */
    public static Double calcCourseRating(final List<Attendance> att) {
        Double rating = 0.0;
        int counter = 0;
        for (Attendance a : att) {
            if (a.getMark() != null) {
                counter++;
                rating += a.getMark();
            }
        }
        return counter == 0 ? null : rating / counter;
    }

    /**
     * Method to calculate {@link by.alex.testing.domain.User}
     * {@link by.alex.testing.domain.Course} attendance percent.
     *
     * @param att {@link List} of
     *            {@link by.alex.testing.domain.User}
     *            {@link Attendance}'s
     * @return {@link Double} {@link by.alex.testing.domain.User}
     * attendance percent
     */
    public static Double calcAttendancePercent(final List<Attendance> att) {
        double percent = 0.0;
        int counter = 0;
        for (Attendance a : att) {
            counter++;
            AttendanceStatus status = a.getStatus();
            if (!status.equals(AttendanceStatus.NOT_PRESENT)) {
                percent += 1;
            }
        }
        return counter == 0 ? null : (percent / counter) * HUNDRED_PERCENT;
    }
}
