package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Lesson;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LessonValidator extends BaseValidator {

    /**
     * Method to validate {@link Lesson} fields.
     *
     * @param lesson {@link Lesson} to validate
     * @return {@link List} of errors if validated unsuccessful,
     * otherwise empty
     */
    public static List<String> validate(final Lesson lesson) {
        String title = lesson.getTitle();
        LocalDateTime startDate = lesson.getStartDate();
        LocalDateTime endDate = lesson.getEndDate();

        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(title)
                || !validatePattern(title, RegexStorage.TITLE_PATTERN)) {
            String error = MessageManager.INSTANCE
                    .getMessage(MessageConstant.LESSON_TITLE_ERROR);
            errors.add(error);
        }
        if (!validateDates(startDate, endDate)) {
            String error = MessageManager.INSTANCE
                    .getMessage(MessageConstant.DATE_ERROR);
            errors.add(error);
        }
        return errors;
    }

    private static boolean validateDates(final LocalDateTime startDate,
                                         final LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return startDate.isBefore(endDate);
        }
        return false;
    }
}
