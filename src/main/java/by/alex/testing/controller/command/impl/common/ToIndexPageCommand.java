package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToIndexPageCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ToIndexPageCommand.class);

    public ToIndexPageCommand() {
        ServiceFactory.getInstance();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) {

        logger.info("To index page command received");
        return new ViewResolver(PageConstant.INDEX_PAGE);
    }
}
