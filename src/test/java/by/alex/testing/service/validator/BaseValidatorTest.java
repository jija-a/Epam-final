package by.alex.testing.service.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BaseValidatorTest {

    @DataProvider(name = "correctDataPattern")
    private Object[][] provideCorrectDataForPattern() {
        return new Object[][]{
                {"Course Name", "^.{3,150}$"},
                {"Course Name 2", "^.{3,150}$"},
                {"password123W", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"},
                {"Name", "^[a-zA-Z]{3,25}$"},
                {"Login123", "^[a-zA-Z0-9._-]{3,255}$"},
        };
    }

    @DataProvider(name = "incorrectDataPattern")
    private Object[][] provideIncorrectDataForPattern() {
        return new Object[][]{
                {"L", "^.{3,150}$"},
                {"password", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"},
                {"Name123", "^[a-zA-Z]{3,25}$"},
                {"L2", "^[a-zA-Z0-9._-]{3,255}$"},
        };
    }

    @DataProvider(name = "correctDataPositiveNumber")
    private Object[] provideCorrectDataForPositiveNumber() {
        return new Object[]{
                1, 2, 3, 4, 5
        };
    }

    @DataProvider(name = "incorrectDataPositiveNumber")
    private Object[] provideIncorrectDataForPositiveNumber() {
        return new Object[]{
                -1, -3, -1234123
        };
    }

    @Test(dataProvider = "correctDataPattern")
    public void testValidatePatternSuccess(String value, String pattern) {
        boolean actual = BaseValidator.validatePattern(value, pattern);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "incorrectDataPattern")
    public void testValidatePatternFail(String value, String pattern) {
        boolean actual = BaseValidator.validatePattern(value, pattern);
        Assert.assertFalse(actual);
    }

    @Test(dataProvider = "correctDataPositiveNumber")
    public void testIsPositiveNumberSuccess(Integer number) {
        boolean actual = BaseValidator.isPositiveNumber(number);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "incorrectDataPositiveNumber")
    public void testIsPositiveNumberFail(Integer number) {
        boolean actual = BaseValidator.isPositiveNumber(number);
        Assert.assertFalse(actual);
    }
}