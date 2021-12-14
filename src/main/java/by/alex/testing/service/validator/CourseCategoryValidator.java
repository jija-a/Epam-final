package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CourseCategoryValidator extends BaseValidator {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseCategoryValidator.class);

    private CourseCategoryValidator(){
    }

    public static List<String> validate(CourseCategory category) {
        logger.info("Validating course category");
        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(category.getName())
                || !validatePattern(category.getName(), RegexStorage.TITLE_PATTERN)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.NAME_ERROR));
        }
        return errors;
    }

}
