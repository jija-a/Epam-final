package by.alex.testing.service.validator;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.CourseCategory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class CourseValidatorTest {

    @DataProvider(name = "correctData")
    private Object[] provideCorrectData() {
        return new Object[]{
                Course.builder().name("Course").build(),
                Course.builder().name("Java").build(),
                Course.builder().name("JavaScript").build(),
                Course.builder().name("16 School 11 B").build(),
                Course.builder().name("LearnSimple").build(),
        };
    }

    @DataProvider(name = "incorrectData")
    private Object[] provideIncorrectData() {
        return new Object[]{
                Course.builder().name("Course...//---asd").build(),
                Course.builder().name("Course..-dasdaweq").build(),
                Course.builder().name("Java---Script").build(),
                Course.builder().name("16!!!!School!!!)W!))!11!_!_@_!_B").build(),
                Course.builder().name("..123///(((EQW))DASPFLPMORKIWQJOEI!@#!@))#!!@#))!))))@)A").build(),
        };
    }

    @Test(dataProvider = "correctData")
    public void testValidationSuccess(Course actual) {
        List<String> errors = CourseValidator.validate(actual);
        Assert.assertEquals(errors.size(), 0);
    }

    @Test(dataProvider = "incorrectData")
    public void testValidationFail(Course actual) {
        List<String> errors = CourseValidator.validate(actual);
        Assert.assertTrue(errors.size() > 0);
    }
}