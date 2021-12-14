package by.alex.testing.service.validator;

import by.alex.testing.domain.User;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class UserValidatorTest {

    @DataProvider(name = "correctData")
    private Object[] provideCorrectData() {
        return new Object[]{
                User.builder()
                        .login("MySuperLogin1")
                        .password(new char[]{'1', '2', '3', '4', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Alex")
                        .lastName("Nered")
                        .build(),
                User.builder()
                        .login("AleXMercer3000")
                        .password(new char[]{'1', '2', 'Q', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Julia")
                        .lastName("Helenz")
                        .build(),
                User.builder()
                        .login("Andrey22")
                        .password(new char[]{'1', '3', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Andrey")
                        .lastName("Slat")
                        .build(),
                User.builder()
                        .login("Moralez21")
                        .password(new char[]{'W', 'W', 'W', '4', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Austin")
                        .lastName("Moralez")
                        .build(),
                User.builder()
                        .login("Vito14")
                        .password(new char[]{'L', '4', 'A', 'R', '9', '6', '7', '8', 'q', 'W'})
                        .firstName("Vito")
                        .lastName("Skaletto")
                        .build()
        };
    }

    @DataProvider(name = "incorrectData")
    private Object[] provideIncorrectData() {
        return new Object[]{
                User.builder()
                        .login("MySuperLogin1______123______ALEX")
                        .password(new char[]{'1', '2', '3', '4', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Alex")
                        .lastName("Nered")
                        .build(),
                User.builder()
                        .login("AleXMercer3000")
                        .password(new char[]{'1', '2', 'Q', '_', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Julia")
                        .lastName("Helenz")
                        .build(),
                User.builder()
                        .login("Andrey22")
                        .password(new char[]{'1', '3', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Andrey1234")
                        .lastName("Slat")
                        .build(),
                User.builder()
                        .login("Moralez21")
                        .password(new char[]{'W', 'W', 'W', '4', '5', '6', '7', '8', 'q', 'W'})
                        .firstName("Austin")
                        .lastName("Moralez132erwWQEQW31234")
                        .build(),
                User.builder()
                        .login("Vito")
                        .password(new char[]{'L', '4', 'A', 'R', '9', '6', '7', '8', 'q', 'W'})
                        .firstName("Vito")
                        .lastName("Skaletto")
                        .build()
        };
    }

    @Test(dataProvider = "correctData")
    public void testValidationSuccess(User actual) {
        List<String> errors = UserValidator.validate(actual);
        Assert.assertEquals(errors.size(), 0);
    }

    @Test(dataProvider = "incorrectData")
    public void testValidationFail(User actual) {
        List<String> errors = UserValidator.validate(actual);
        Assert.assertTrue(errors.size() > 0);
    }
}