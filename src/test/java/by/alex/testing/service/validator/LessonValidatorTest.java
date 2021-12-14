package by.alex.testing.service.validator;

import by.alex.testing.domain.Course;
import by.alex.testing.domain.Lesson;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LessonValidatorTest {

    DateTimeFormatter formatter;

    @BeforeClass
    public void init(){
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @DataProvider(name = "correctData")
    private Object[] provideCorrectData() {
        return new Object[]{
                Lesson.builder()
                        .title("Lesson")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("First Lesson")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("Second Lesson")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("Commands")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("Java Lesson-HD Top")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
        };
    }

    @DataProvider(name = "incorrectData")
    private Object[] provideIncorrectData() {
        return new Object[]{
                Lesson.builder()
                        .title("Lesson")
                        .startDate(LocalDateTime.parse("2013-04-05 15:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("First Lesson")
                        .startDate(LocalDateTime.parse("2013-04-05 21:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("Second Lesson")
                        .startDate(LocalDateTime.parse("2013-04-06 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("Commands!!!!!!!!!")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder()
                        .title("Java Lesson-HD Top()((())DAS")
                        .startDate(LocalDateTime.parse("2013-04-05 14:00", formatter))
                        .endDate(LocalDateTime.parse("2013-04-05 14:45", formatter))
                        .build(),
                Lesson.builder().title("Course...//---asd").build()
        };
    }

    @Test(dataProvider = "correctData")
    public void testValidationSuccess(Lesson actual) {
        List<String> errors = LessonValidator.validate(actual);
        Assert.assertEquals(errors.size(), 0);
    }

    @Test(dataProvider = "incorrectData")
    public void testValidationFail(Lesson actual) {
        List<String> errors = LessonValidator.validate(actual);
        Assert.assertTrue(errors.size() > 0);
    }
}