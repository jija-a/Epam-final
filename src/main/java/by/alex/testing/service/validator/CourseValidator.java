package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Course;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CourseValidator extends BaseValidator {

    /**
     * Method to validate {@link Course} fields.
     *
     * @param course {@link Course} to validate
     * @return {@link List} of errors if validated unsuccessful,
     * otherwise empty
     */
    public static List<String> validate(final Course course) {
        String error = MessageManager.INSTANCE
                .getMessage(MessageConstant.COURSE_TITLE_ERROR);
        String name = course.getName();

        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(name)
                || !validatePattern(name, RegexStorage.TITLE_PATTERN)) {
            errors.add(error);
        }
        return errors;
    }
}
