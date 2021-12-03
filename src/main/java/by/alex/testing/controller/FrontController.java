package by.alex.testing.controller;

import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.CommandProvider;
import by.alex.testing.domain.UnknownEntityException;
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

@WebServlet(urlPatterns = "/controller")
public class FrontController extends HttpServlet {

    private static final Logger logger =
            LoggerFactory.getLogger(FrontController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            this.handleRequest(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Controller provided exception: ", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            this.handleRequest(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Controller provided exception: ", e);
        }
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        logger.debug("Handling controller request");

        String commandName = req.getParameter(RequestConstant.COMMAND);
        if (StringUtils.isNullOrEmpty(commandName)) {
            logger.warn("Command name is empty");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Command command = CommandProvider.resolveCommand(commandName);
            ViewResolver resolver = command.execute(req, resp);
            this.dispatch(req, resp, resolver);
        } catch (NotEnoughParametersException | UnknownEntityException | NumberFormatException e) {
            logger.error(e.getMessage(), e);
            req.getSession().setAttribute(RequestConstant.ERROR,
                    MessageManager.INSTANCE.getMessage(MessageConstant.WRONG_PARAMETERS));
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            logger.error("Service provided exception to front controller," +
                    " redirecting to 500 error page: : ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unhandled exception, redirecting to 500 error page: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp,
                          ViewResolver resolver) throws ServletException, IOException {

        String view = resolver.getView();
        ViewResolver.ResolveAction action = resolver.getResolveAction();
        if (action.equals(ViewResolver.ResolveAction.FORWARD)) {
            logger.info("Forwarding to '{}'", view);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
            dispatcher.forward(req, resp);
        } else {
            logger.info("Redirecting to '{}'", view);
            resp.sendRedirect(req.getContextPath() + view);
        }
    }

}
