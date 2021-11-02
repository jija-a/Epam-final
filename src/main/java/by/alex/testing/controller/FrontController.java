package by.alex.testing.controller;

import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.command.CommandFactory;
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
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        this.handleRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
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
            Command controller = CommandFactory.resolveCommand(commandName);
            String page = controller.execute(req, resp);
            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Service provided exception to front controller: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
