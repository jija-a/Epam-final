package by.alex.testing.service.validator;

import java.util.regex.Pattern;

public abstract class BaseValidator {

    protected static boolean validatePattern(final String value,
                                             final String pattern) {
        return Pattern.matches(pattern, value);
    }

    protected static boolean isPositiveNumber(final Integer number) {
        if (number == null) {
            return true;
        }
        return number > -1;
    }
}
