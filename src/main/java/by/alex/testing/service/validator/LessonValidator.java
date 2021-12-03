package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Lesson;
import com.mysql.cj.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LessonValidator extends BaseValidator {

    public static List<String> validate(Lesson lesson) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(lesson.getTitle())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.NAME_ERROR));
        }
        if (!validateDates(lesson.getStartDate(), lesson.getEndDate())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_DATES_ERROR));
        }
        return errors;
    }

    private static boolean validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }
}
