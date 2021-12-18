package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.CourseCategory;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CourseCategoryValidator extends BaseValidator {

    /**
     * Method to validate {@link CourseCategory} fields.
     *
     * @param category {@link CourseCategory} to validate
     * @return {@link List} of errors if validated unsuccessful,
     * otherwise empty
     */
    public static List<String> validate(final CourseCategory category) {
        String error = MessageManager.INSTANCE
                .getMessage(MessageConstant.CATEGORY_TITLE_ERROR);
        String name = category.getName();

        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(name)
                || !validatePattern(name, RegexStorage.TITLE_PATTERN)) {
            errors.add(error);
        }
        return errors;
    }

}
