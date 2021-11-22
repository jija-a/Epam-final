package by.alex.testing.tag;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CopyrightTag extends TagSupport {

    private static final Logger logger =
            LoggerFactory.getLogger(CopyrightTag.class);

    @Override
    public int doStartTag() throws JspTagException {
        logger.debug("Copyright tag started");
        String copyright = MessageManager.INSTANCE.getMessage(MessageConstant.COPYRIGHT);
        try {
            JspWriter out = pageContext.getOut();
            out.write(copyright);
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }
}
