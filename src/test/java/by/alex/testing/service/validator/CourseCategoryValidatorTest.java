package by.alex.testing.service.validator;

import by.alex.testing.domain.CourseCategory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class CourseCategoryValidatorTest {

    @DataProvider(name = "correctData")
    private Object[] provideCorrectData() {
        return new Object[]{
                CourseCategory.builder().name("LengthMoreThan3LessThan150").build(),
                CourseCategory.builder().name("JAVA").build(),
                CourseCategory.builder().name("JavaScript").build()
        };
    }

    @DataProvider(name = "incorrectData")
    private Object[] provideIncorrectData() {
        return new Object[]{
                CourseCategory.builder().name(")").build(),
                CourseCategory.builder().name("((()))((()))(((()))))(((()))").build(),
                CourseCategory.builder().name("....////.....11``1.`").build()
        };
    }

    @Test(dataProvider = "correctData")
    public void testValidationSuccess(CourseCategory actual) {
        List<String> errors = CourseCategoryValidator.validate(actual);
        Assert.assertEquals(errors.size(), 0);
    }

    @Test(dataProvider = "incorrectData")
    public void testValidationFail(CourseCategory actual) {
        List<String> errors = CourseCategoryValidator.validate(actual);
        Assert.assertTrue(errors.size() > 0);
    }
}