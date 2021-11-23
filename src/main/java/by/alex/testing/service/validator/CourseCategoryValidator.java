package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.CourseCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CourseCategoryValidator {

    private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я\\s]{3,128}$";

    public static List<String> validate(CourseCategory category) {
        List<String> errors = new ArrayList<>();
        if (!validateName(category.getName())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.NAME_ERROR));
        }
        return errors;
    }

    public static boolean validateName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }
}
