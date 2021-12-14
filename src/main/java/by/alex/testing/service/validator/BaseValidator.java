package by.alex.testing.service.validator;

import java.util.regex.Pattern;

public class BaseValidator {

    protected static boolean validatePattern(String value, String pattern) {
        return Pattern.matches(pattern, value);
    }

    protected static boolean isPositiveNumber(Integer number) {
        if (number == null) return true;
        return number > -1;
    }
}
