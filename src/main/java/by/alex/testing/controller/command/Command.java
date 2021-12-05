package by.alex.testing.controller.command;

import by.alex.testing.controller.NotEnoughParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.service.AccessDeniedException;
import by.alex.testing.service.PaginationService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    Logger logger =
            LoggerFactory.getLogger(Command.class);


    ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, NotEnoughParametersException, AccessDeniedException;

    default String createRedirectURL(HttpServletRequest req, String command) {
        return req.getServletPath() + "?" + RequestConstant.COMMAND + "=" + command;
    }

    default int definePagination(HttpServletRequest req, Integer entitiesQty,
                                 Integer recordsPerPage, Integer defaultRecPerPage) {
        PaginationService paginationService = ServiceFactory.getInstance().getPaginationService();

        int page = req.getParameter(RequestConstant.PAGE_NUMBER) != null ?
                Integer.parseInt(req.getParameter(RequestConstant.PAGE_NUMBER)) : 1;

        int numberOfPages = paginationService.defineNumberOfPages(entitiesQty, recordsPerPage);

        if (page < 1 || page > numberOfPages) {
            page = 1;
        }

        if (recordsPerPage < 1) {
            recordsPerPage = 5;
        }

        req.setAttribute(RequestConstant.NUMBER_OF_PAGES, numberOfPages);
        req.setAttribute(RequestConstant.PAGE_NUMBER, page);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recordsPerPage);

        return paginationService.defineStartEntityNumber(page, recordsPerPage);
    }

}
