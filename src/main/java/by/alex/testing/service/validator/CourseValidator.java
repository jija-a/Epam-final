package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Course;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CourseValidator extends BaseValidator {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseValidator.class);

    private CourseValidator() {
    }

    public static List<String> validate(Course course) {
        logger.info("Validating course");
        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(course.getName())
                || !validatePattern(course.getName(), RegexStorage.TITLE_PATTERN)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.COURSE_NAME_ERROR));
        }
        return errors;
    }
}
