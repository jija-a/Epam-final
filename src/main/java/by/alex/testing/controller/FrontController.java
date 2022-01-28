package by.alex.testing.controller;

import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.CommandProvider;
import by.alex.testing.service.AccessException;
import by.alex.testing.service.ServiceException;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class FrontController. This servlet handles all
 * requests by the client and then processes them according to specified command
 * name.
 */
@WebServlet(urlPatterns = "/controller")
public class FrontController extends HttpServlet {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(FrontController.class.getName());

    /**
     * @param req  {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doPost(final HttpServletRequest req,
                          final HttpServletResponse resp) {

        try {
            this.handleRequest(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("Controller provided exception: ", e);
        }
    }

    /**
     * @param req  {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp) {

        try {
            this.handleRequest(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("Controller provided exception: ", e);
        }
    }

    /**
     * Handles all requests coming from the client by executing the specified
     * command name in a request.
     *
     * @param req  {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException
     * @throws ServletException
     */
    private void handleRequest(final HttpServletRequest req,
                               final HttpServletResponse resp)
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
        } catch (ParametersException | NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (AccessException e) {
            LOGGER.error(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            LOGGER.error("Service provided exception to front controller,"
                    + " redirecting to 500 error page: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.error("Unhandled exception, "
                    + "redirecting to 500 error page: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Implements PRG pattern by checking action type
     * specified by the invoked method.
     *
     * @param req      {@link HttpServletRequest}
     * @param resp     {@link HttpServletResponse}
     * @param resolver {@link ViewResolver}
     * @throws ServletException
     * @throws IOException
     * @see ViewResolver
     * @see by.alex.testing.controller.ViewResolver.ResolveAction
     */
    private void dispatch(final HttpServletRequest req,
                          final HttpServletResponse resp,
                          final ViewResolver resolver)
            throws ServletException, IOException {

        String view = resolver.getView();
        ViewResolver.ResolveAction action = resolver.getResolveAction();
        if (action.equals(ViewResolver.ResolveAction.FORWARD)) {
            LOGGER.info("Forwarding to: '{}'", view);
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher(view);
            dispatcher.forward(req, resp);
        } else {
            LOGGER.info("Redirecting to: '{}'", view);
            resp.sendRedirect(req.getContextPath() + view);
        }
    }

}
