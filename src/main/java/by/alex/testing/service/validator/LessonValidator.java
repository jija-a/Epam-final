package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Lesson;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LessonValidator extends BaseValidator {

    private static final Logger logger =
            LoggerFactory.getLogger(LessonValidator.class);

    public static List<String> validate(Lesson lesson) {
        logger.info("Validating lesson");
        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(lesson.getTitle())
                || !validatePattern(lesson.getTitle(), RegexStorage.TITLE_PATTERN)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.NAME_ERROR));
        }
        if (!validateDates(lesson.getStartDate(), lesson.getEndDate())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.TEST_DATES_ERROR));
        }
        return errors;
    }

    private static boolean validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return startDate.isBefore(endDate);
        }
        return false;
    }
}
