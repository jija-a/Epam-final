package by.alex.testing.controller.command;

import by.alex.testing.controller.NotEnoughParametersException;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ParamsFromRequestHandler {

    private ParamsFromRequestHandler() {
    }

    public static Long getLongParameter(HttpServletRequest req, String paramName)
            throws NotEnoughParametersException {

        String paramFromReq = req.getParameter(paramName);
        Long param;
        if (StringUtils.isNullOrEmpty(paramFromReq)) {
            param = (Long) req.getSession().getAttribute(paramName);
            if (param == null) {
                throw new NotEnoughParametersException();
            }
        } else {
            param = Long.valueOf(paramFromReq);
            req.getSession().setAttribute(paramName, param);
        }
        return param;
    }
}
