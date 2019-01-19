package by.etc.payroll.controller;

import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.factory.ActionFactory;
import by.etc.payroll.dao.dbmanager.ConnectionPool;
import by.etc.payroll.controller.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static Logger LOG = LogManager.getLogger(Controller.class);
    private static final int PAGE_NOT_FOUND_ERROR = 404;

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
        super.destroy();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            LOG.info("Get new request ...");

            ActionFactory factory = ActionFactory.getInstance();
            ActionCommand command = factory.defineCommand(request);
            command.execute(request, response);


        } catch (CommandException e) {
            LOG.error(e.getMessage(), e);
            response.sendError(PAGE_NOT_FOUND_ERROR);
        }


    }
}
