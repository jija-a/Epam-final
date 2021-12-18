package by.alex.testing.service.validator;

import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AttendanceValidatorTest {

    @DataProvider(name = "correctData")
    private Object[] provideCorrectData() {
        return new Object[]{
                Attendance.builder()
                        .mark(6)
                        .status(AttendanceStatus.PRESENT)
                        .build(),
                Attendance.builder()
                        .mark(1)
                        .status(AttendanceStatus.PRESENT)
                        .build()
        };
    }

    @DataProvider(name = "incorrectData")
    private Object[] provideIncorrectData() {
        return new Object[]{
                Attendance.builder()
                        .mark(6)
                        .status(AttendanceStatus.NOT_PRESENT)
                        .build(),
                Attendance.builder()
                        .mark(-1)
                        .status(AttendanceStatus.PRESENT)
                        .build()
        };
    }

    @Test(dataProvider = "correctData")
    public void testValidationSuccess(Attendance actual) {
        List<String> errors = AttendanceValidator.validate(actual);
        Assert.assertEquals(errors.size(), 0);
    }

    @Test(dataProvider = "incorrectData")
    public void testValidationFail(Attendance actual) {
        List<String> errors = AttendanceValidator.validate(actual);
        Assert.assertTrue(errors.size() > 0);
    }
}