package by.alex.testing.controller.command;

import by.alex.testing.controller.ParametersException;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.PaginationService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command pattern.
 */
public interface Command {

    /**
     * Default max entities quantity on page.
     */
    int DEFAULT_REC_PER_PAGE = 5;

    /**
     * Execution method for command.
     *
     * @param req  {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @return {@link ViewResolver}
     * @throws ServiceException    if was logic or
     *                             {@link by.alex.testing.dao.DaoException}
     *                             exception
     * @throws ParametersException if method does not
     *                             have enough parameters for
     *                             execution
     * @throws AccessException     if was logic exception in service layer
     */
    ViewResolver execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServiceException, ParametersException, AccessException;

    /**
     * Method creates url that will be forwarded after command execution.
     *
     * @param req     {@link HttpServletRequest}
     * @param command {@link Command}
     * @return {@link String} url
     */
    default String createRedirectURL(HttpServletRequest req, String command) {
        return req.getServletPath() + "?"
                + RequestConstant.COMMAND + "=" + command;
    }

    /**
     * Method defines pagination on jsp page.
     * Sets number of pages, page and records per page into request.
     *
     * @param req            {@link HttpServletRequest}
     * @param qty            {@link Integer} quantity of entities
     * @param recordsPerPage {@link Integer} how many entities
     *                       can be shown on page
     * @return {@link Integer} start number of entity in DB
     */
    default int definePagination(HttpServletRequest req, Integer qty,
                                 Integer recordsPerPage) {

        PaginationService paginationService =
                ServiceFactory.getInstance().getPaginationService();
        String pageNumber = req.getParameter(RequestConstant.PAGE_NUMBER);
        if (recordsPerPage == null || recordsPerPage < 1) {
            recordsPerPage = DEFAULT_REC_PER_PAGE;
        }
        int page = pageNumber != null ? Integer.parseInt(pageNumber) : 1;
        int numberOfPages =
                paginationService.defineNumberOfPages(qty, recordsPerPage);
        if (page < 1 || page > numberOfPages) {
            page = 1;
        }
        req.setAttribute(RequestConstant.NUMBER_OF_PAGES, numberOfPages);
        req.setAttribute(RequestConstant.PAGE_NUMBER, page);
        req.setAttribute(RequestConstant.RECORDS_PER_PAGE, recordsPerPage);
        return paginationService.defineStartNumber(page, recordsPerPage);
    }

}
