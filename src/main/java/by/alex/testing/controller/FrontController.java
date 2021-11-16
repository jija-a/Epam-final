package by.alex.testing.controller;

import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.CommandProvider;
import by.alex.testing.service.ServiceException;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FrontController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.handleRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.debug("Handling controller request");

        String commandName = req.getParameter(RequestConstant.COMMAND);

        if (StringUtils.isNullOrEmpty(commandName)) {
            LOGGER.warn("Command name is empty");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Command command = CommandProvider.resolveCommand(commandName);
            ViewResolver resolver = command.execute(req, resp);
            this.dispatch(req, resp, resolver);
        } catch (ServiceException e) {
            LOGGER.error("Service provided exception to front controller: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp,
                          ViewResolver resolver) throws ServletException, IOException {

        String view = resolver.getView();
        switch (resolver.getResolveAction()) {
            case FORWARD:
                LOGGER.info("Forwarding to '{}'", view);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
                dispatcher.forward(req, resp);
                break;
            case REDIRECT:
                LOGGER.info("Redirecting to '{}'", view);
                resp.sendRedirect(req.getContextPath() + view);
                break;
            default:
                LOGGER.info("Redirecting to '{}'", view);
                resp.sendRedirect(req.getContextPath() + PageConstant.HOME_PAGE);
                break;
        }

    }

}
