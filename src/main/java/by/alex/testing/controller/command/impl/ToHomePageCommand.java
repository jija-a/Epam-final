package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.dao.InitializingError;
import by.alex.testing.dao.pool.ConnectionPool;
import by.alex.testing.dao.pool.DatabaseConfig;
import by.alex.testing.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToHomePageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServiceException {

        return PageConstant.HOME_PAGE;
    }
}
