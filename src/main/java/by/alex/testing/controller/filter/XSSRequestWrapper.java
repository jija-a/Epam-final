package by.alex.testing.controller.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Handler for {@link XSSFilter}.
 */
public final class XSSRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Class constructor.
     *
     * @param servletRequest {@link HttpServletRequest}
     * @see HttpServletRequest
     */
    public XSSRequestWrapper(final HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(final String parameter) {

        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return new String[0];
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(final String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    @Override
    public String getHeader(final String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);

    }

    private String cleanXSS(final String value) {
        String rep = value;
        rep = rep.replace("<", "&lt;").replace(">", "&gt;");
        rep = rep.replace("\\(", "&#40;").replace("\\)", "&#41;");
        rep = rep.replace("'", "&#39;");
        rep = rep.replaceAll("eval\\((.*)\\)", "");
        rep = rep.replaceAll("[\"'][\\s]*javascript:(.*)[\"']", "\"\"");
        rep = rep.replace("script", "");
        return rep;
    }
}
