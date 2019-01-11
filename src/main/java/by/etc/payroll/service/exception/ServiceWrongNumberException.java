package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongNumberException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 1833114245931041L;

    public ServiceWrongNumberException() {
        super();
    }

    public ServiceWrongNumberException(String message) {
        super(message);
    }

    public ServiceWrongNumberException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongNumberException(Exception e) {
        super (e);
    }
}
