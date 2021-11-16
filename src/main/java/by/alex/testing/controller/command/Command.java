package by.alex.testing.controller.command;

import by.alex.testing.controller.ViewResolver;
import by.alex.testing.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException;
}
