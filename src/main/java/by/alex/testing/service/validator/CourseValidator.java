package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CourseValidator {

    private static final String NAME_PATTERN = "^.{3,150}$";

    public static List<String> validate(Course course) {
        List<String> errors = new ArrayList<>();
        if (!validateName(course.getName())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.COURSE_NAME_ERROR));
        }
        return errors;
    }

    public static boolean validateName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }
}
