package by.alex.testing.controller.validator;

import com.mysql.cj.util.StringUtils;

/**
 * Parameter validator for controller layer.
 */
public final class ParameterValidator {

    private ParameterValidator() {
    }

    /**
     * @param param  {@link javax.servlet.http.HttpServletRequest} parameter
     * @param params {@link javax.servlet.http.HttpServletRequest} parameters
     * @return true if not empty, else false
     */
    public static boolean isNullOrEmpty(final String param,
                                        final String... params) {
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
