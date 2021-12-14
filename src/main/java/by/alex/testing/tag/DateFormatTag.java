package by.alex.testing.tag;

import org.apache.taglibs.standard.tag.common.core.Util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateFormatTag extends TagSupport {

    protected Temporal temporal;
    protected String pattern;
    private String varVal;
    private int scope;

    public DateFormatTag() {
        super();
        init();
    }

    private void init() {

        this.pattern = this.varVal = null;
        this.temporal = null;
        this.scope = PageContext.PAGE_SCOPE;
    }

    public void setVar(final String varVal) {
        this.varVal = varVal;
    }

    public void setScope(final String scope) {
        this.scope = Util.getScope(scope);
    }

    public void setValue(final Temporal value) {
        this.temporal = value;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int doEndTag() throws JspException {

        String formatted = null;

        if (this.temporal == null) {
            if (this.varVal != null) {
                this.pageContext.removeAttribute(this.varVal, this.scope);
            }
            return EVAL_PAGE;
        }

        if (this.pattern != null) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.pattern);
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
