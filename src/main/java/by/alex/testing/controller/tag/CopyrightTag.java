package by.alex.testing.controller.tag;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Class provides localized copyright message to view layer.
 */
public final class CopyrightTag extends TagSupport {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CopyrightTag.class);

    @Override
    public int doStartTag() throws JspTagException {
        LOGGER.debug("Copyright tag started");
        String copyright =
                MessageManager.INSTANCE.getMessage(MessageConstant.COPYRIGHT);
        try {
            JspWriter out = pageContext.getOut();
            out.write(copyright);
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }
}
