package by.alex.testing.controller.command;

import by.alex.testing.controller.ParametersException;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Class get {@link HttpServletRequest} parameters from session or
 * request.
 */
public final class ParamFromReqHandler {

    private ParamFromReqHandler() {
    }

    /**
     * Method get {@link Long} parameter from session or request.
     * Then sets it to session.
     *
     * @param req       {@link HttpServletRequest}
     * @param paramName {@link String} parameter name
     * @return {@link Long} parameter
     * @throws ParametersException if parameter not found
     */
    public static Long getLongParameter(final HttpServletRequest req,
                                        final String paramName)
            throws ParametersException {

        String paramFromReq = req.getParameter(paramName);
        Long param;
        if (StringUtils.isNullOrEmpty(paramFromReq)) {
            param = (Long) req.getSession().getAttribute(paramName);
            if (param == null) {
                throw new ParametersException();
            }
        } else {
            param = Long.valueOf(paramFromReq);
            req.getSession().setAttribute(paramName, param);
        }
        return param;
    }
}
