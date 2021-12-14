package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.User;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends BaseValidator {

    private static final Logger logger =
            LoggerFactory.getLogger(UserValidator.class);

    private UserValidator() {
    }

    public static List<String> validate(User user) {
        logger.info("Validating user");
        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(user.getLogin()) || !validatePattern(user.getLogin(), RegexStorage.LOGIN_PATTERN)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.LOGIN_PATTERN_ERROR));
        }
        if (StringUtils.isNullOrEmpty(String.valueOf(user.getPassword()))
                || !validatePattern(String.valueOf(user.getPassword()), RegexStorage.PASSWORD_PATTERN)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.PASSWORD_PATTERN_ERROR));
        }
        if (StringUtils.isNullOrEmpty(user.getFirstName()) || StringUtils.isNullOrEmpty(user.getLastName())
                || !validatePattern(user.getFirstName(), RegexStorage.NAME_PATTERN)
                || !validatePattern(user.getLastName(), RegexStorage.NAME_PATTERN)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.NAME_ERROR));
        }
        return errors;
    }
}
