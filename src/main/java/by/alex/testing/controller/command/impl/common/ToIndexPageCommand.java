package by.alex.testing.controller.command.impl.common;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ToIndexPageCommand implements Command {

    @Override
    public ViewResolver execute(final HttpServletRequest req,
                                final HttpServletResponse resp) {

        return new ViewResolver(PageConstant.INDEX_PAGE);
    }
}
