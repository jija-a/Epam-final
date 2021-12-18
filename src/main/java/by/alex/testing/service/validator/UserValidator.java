package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.User;
import by.alex.testing.service.RegexStorage;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends BaseValidator {

    /**
     * Method to validate {@link User} fields.
     *
     * @param user {@link User} to validate
     * @return {@link List} of errors if validated unsuccessful,
     * otherwise empty
     */
    public static List<String> validate(final User user) {
        String fName = user.getFirstName();
        String lName = user.getLastName();
        String login = user.getLogin();
        String psw = String.valueOf(user.getPassword());

        List<String> errors = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(user.getLogin())
                || !validatePattern(login, RegexStorage.LOGIN_PATTERN)) {
            String error = MessageManager.INSTANCE
                    .getMessage(MessageConstant.LOGIN_REG_ERROR);
            errors.add(error);
        }
        if (StringUtils.isNullOrEmpty(psw)
                || !validatePattern(psw, RegexStorage.PASSWORD_PATTERN)) {
            String error = MessageManager.INSTANCE
                    .getMessage(MessageConstant.PSW_REG_ERROR);
            errors.add(error);
        }
        if (StringUtils.isNullOrEmpty(fName)
                || StringUtils.isNullOrEmpty(lName)
                || !validatePattern(fName, RegexStorage.NAME_PATTERN)
                || !validatePattern(lName, RegexStorage.NAME_PATTERN)) {
            String error = MessageManager.INSTANCE
                    .getMessage(MessageConstant.USER_NAME_ERROR);
            errors.add(error);
        }
        return errors;
    }
}
