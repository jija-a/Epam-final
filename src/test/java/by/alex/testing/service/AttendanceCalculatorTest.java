package by.alex.testing.service;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class AttendanceCalculatorTest {

    @DataProvider(name = "dataForCourseRating")
    private Object[][] provideDataForCourseRating() {
        return new Object[][]{
                {new ArrayList<>() {{
                    add(Attendance.builder().mark(4).build());
                    add(Attendance.builder().mark(8).build());
                    add(Attendance.builder().mark(3).build());
                    add(Attendance.builder().mark(5).build());
                    add(Attendance.builder().mark(5).build());
                }}, 5},
                {new ArrayList<>() {{
                    add(Attendance.builder().mark(2).build());
                    add(Attendance.builder().mark(2).build());
                    add(Attendance.builder().mark(2).build());
                    add(Attendance.builder().mark(2).build());
                    add(Attendance.builder().mark(2).build());
                }}, 2},
                {new ArrayList<>() {{
                    add(Attendance.builder().mark(5).build());
                    add(Attendance.builder().mark(5).build());
                    add(Attendance.builder().mark(5).build());
                    add(Attendance.builder().mark(5).build());
                    add(Attendance.builder().mark(5).build());
                }}, 5}
        };
    }

    @DataProvider(name = "dataForCourseAttendancePercent")
    private Object[][] provideDataForCourseAttendancePercent() {
        return new Object[][]{
                {new ArrayList<>() {{
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                }}, 80},
                {new ArrayList<>() {{
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                }}, 60},
                {new ArrayList<>() {{
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                    add(Attendance.builder().status(AttendanceStatus.NOT_PRESENT).build());
                }}, 0},
        };
    }

    @Test(dataProvider = "dataForCourseRating")
    public void testCalcCourseRating(List<Attendance> attendances, double rating) {
        Double actual = AttendanceCalculator.calcCourseRating(attendances);
        Assert.assertEquals(actual, rating);
    }

    @Test(dataProvider = "dataForCourseAttendancePercent")
    public void testCalcAttendancePercent(List<Attendance> attendances, double percent) {
        Double actual = AttendanceCalculator.calcAttendancePercent(attendances);
        Assert.assertEquals(actual, percent);
    }
}