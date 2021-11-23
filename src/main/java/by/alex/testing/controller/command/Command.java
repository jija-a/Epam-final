package by.alex.testing.controller.command;

import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.dao.DaoException;
import by.alex.testing.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    Logger logger =
            LoggerFactory.getLogger(Command.class);

    ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, DaoException;

    default String createRedirectURL(HttpServletRequest req, String command) {
        return req.getServletPath() + "?" + RequestConstant.COMMAND + "=" + command;
    }

    default int definePagination(HttpServletRequest req, int entitiesQty, int page, int pageLimit) {

        int start = page;
        if (page > 1) {
            start = start - 1;
            start = start * pageLimit + 1;
        }
        start = start - 1;

        Integer numberOfPages = entitiesQty % pageLimit != 0 ?
                entitiesQty / pageLimit + 1 : entitiesQty / pageLimit;

        req.setAttribute(RequestConstant.NUMBER_OF_PAGES, numberOfPages);
        req.setAttribute(RequestConstant.PAGE, page);

        return start;
    }

}
