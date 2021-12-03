package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;

import java.util.regex.Pattern;

public class BaseValidator {

    private static final String LENGTH_PATTERN = "\\w+\\.{4,25}";

    public static String validateLength(String line){
        String error = "";
        if (!Pattern.matches(LENGTH_PATTERN, line)) {
            error = MessageManager.INSTANCE.getMessage(MessageConstant.ONLY_LETTER);
        }
        return error;
    }

    public static boolean isPositiveNumber(Integer number) {
        if (number == null) return true;
        return number > -1;
    }
}
