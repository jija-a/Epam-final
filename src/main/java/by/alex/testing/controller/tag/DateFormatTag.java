package by.alex.testing.controller.tag;

import org.apache.taglibs.standard.tag.common.core.Util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * Class formats date on view layer.
 */
public final class DateFormatTag extends TagSupport {

    /**
     * @see Temporal
     */
    protected Temporal temporal;

    /**
     * {@link java.time.LocalDateTime} pattern.
     */
    protected String pattern;

    /**
     * {@link java.time.LocalDateTime} value.
     */
    private String varVal;

    /**
     * @see PageContext
     */
    private int scope;

    /**
     * Class constructor.
     */
    public DateFormatTag() {
        super();
        init();
    }

    private void init() {
        this.pattern = null;
        this.varVal = null;
        this.temporal = null;
        this.scope = PageContext.PAGE_SCOPE;
    }

    /**
     * @param varVal {@link String} {@link java.time.LocalDateTime} value
     */
    public void setVar(final String varVal) {
        this.varVal = varVal;
    }

    /**
     * @param scope {@link String} {@link PageContext} scope
     */
    public void setScope(final String scope) {
        this.scope = Util.getScope(scope);
    }

    /**
     * @param value {@link java.time.LocalDateTime} value
     */
    public void setValue(final Temporal value) {
        this.temporal = value;
    }

    /**
     * @param pattern {@link java.time.LocalDateTime} pattern
     */
    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int doEndTag() throws JspException {

        String formatted;
        if (this.temporal == null) {
            if (this.varVal != null) {
                this.pageContext.removeAttribute(this.varVal, this.scope);
            }
            return EVAL_PAGE;
        }

        if (this.pattern != null) {
            final DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(this.pattern);
            formatted = formatter.format(this.temporal);
        } else {
            formatted = this.temporal.toString();
        }

        if (this.varVal != null) {
            this.pageContext.setAttribute(this.varVal, formatted, this.scope);
        } else {
            try {
                this.pageContext.getOut().print(formatted);
            } catch (final IOException ioe) {
                throw new JspTagException(ioe.toString(), ioe);
            }
        }

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        init();
    }
}
