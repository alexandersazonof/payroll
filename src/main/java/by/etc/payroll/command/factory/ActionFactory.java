package by.etc.payroll.command.factory;

import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.controller.exception.CommandException;
import by.etc.payroll.command.util.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    private static Logger LOG = LogManager.getLogger(ActionFactory.class);

    private final static String ACTION_STRING = "command";

    private static ActionFactory actionFactory ;

    private ActionFactory() {

    }

    public static ActionFactory getInstance () {
        if (actionFactory == null) {
            actionFactory = new ActionFactory();
        }

        return actionFactory;
    }

    public ActionCommand defineCommand (HttpServletRequest request) throws CommandException {

        LOG.info("Create command for processing request");
        ActionCommand command = null;
        String action = request.getParameter(ACTION_STRING);



        if (action == null || action.isEmpty() || !isContains(action)) {
            LOG.info("Error action , incorrect request ");
            throw new CommandException("Incorrect request");
        }

        CommandFactory commandFactory = CommandFactory.valueOf(action.toUpperCase());
        command = commandFactory.createCommand();

        LOG.info("Create new command : " + commandFactory);
        return command;
    }



    private boolean isContains(String action) {

        CommandFactory[] arrayCommands =  CommandFactory.values();

        for (int i=0;i<arrayCommands.length;i++) {

            if (arrayCommands[i].toString().equalsIgnoreCase(action)) {
                return true;
            }

        }
        return false;
    }
}
