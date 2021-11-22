package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String LOGIN_PATTERN = "^[a-zA-Z0-9._-]{3,}$";

    public static List<String> validate(User user) {
        List<String> errors = new ArrayList<>();
        if (!validateLogin(user.getLogin())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.LOGIN_PATTERN_ERROR));
        }
        if (!validatePassword(user.getPassword())) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.PASSWORD_PATTERN_ERROR));
        }
        return errors;
    }

    private static boolean validateLogin(String login) {
        return Pattern.matches(LOGIN_PATTERN, login);
    }

    private static boolean validatePassword(char[] password) {
        return Pattern.matches(PASSWORD_PATTERN, String.valueOf(password));
    }
}
