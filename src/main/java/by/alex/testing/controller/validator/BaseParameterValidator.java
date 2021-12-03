package by.alex.testing.controller.validator;

import com.mysql.cj.util.StringUtils;

public class BaseParameterValidator {

    private BaseParameterValidator(){
    }

    public static boolean isNullOrEmpty(String param, String... params) {
        if (StringUtils.isNullOrEmpty(param)) {
            return true;
        }
        for (String p : params) {
            if (StringUtils.isNullOrEmpty(p)) {
                return true;
            }
        }
        return false;
    }
}
