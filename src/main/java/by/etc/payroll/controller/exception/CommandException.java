package by.etc.payroll.controller.exception;

import java.io.Serializable;

public class CommandException extends Exception implements Serializable {
    private static final long serialVersionUID = 12891424593041L;
    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception e) {
        super(message, e);
    }

    public CommandException(Exception e) {
        super(e);
    }

}
