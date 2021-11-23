package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Quiz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TestValidator {

    private static final String NAME_PATTERN = "^.{3,150}$";

    public static List<String> validate(Quiz test) {
        List<String> errors = new ArrayList<>();
        if (!validateName(test.getTitle())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_TITLE_ERROR));
        }
        if (!validateNumber(test.getAttempts())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_ATTEMPTS_ERROR));
        }
        if (!validateNumber(test.getMaxScore())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_MAX_SCORE_ERROR));
        }
        if (!validateNumber(test.getTimeToAnswer())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_TIME_ERROR));
        }
        if (!validateDates(test.getStartDate(), test.getEndDate())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_DATES_ERROR));
        }
        return errors;
    }

    public static boolean validateName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }

    public static boolean validateNumber(int number) {
        return number > 0;
    }

    public static boolean validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }
}
