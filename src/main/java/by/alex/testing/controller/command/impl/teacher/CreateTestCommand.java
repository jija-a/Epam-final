package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateTestCommand implements Command {
    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return null;
    }
}
