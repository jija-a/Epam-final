package by.alex.testing.controller.command.impl.page;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToTestCreationPageCommand implements Command {

    private static final Logger logger =
            LoggerFactory.getLogger(ToTestCreationPageCommand.class);

    public ToTestCreationPageCommand() {
        ServiceFactory.getInstance();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp) {

        logger.info("To test creation page command received");
        return new ViewResolver(PageConstant.TEST_CREATION_PAGE);
    }
}
