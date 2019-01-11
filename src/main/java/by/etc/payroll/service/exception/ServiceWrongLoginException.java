package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongLoginException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 18811424593041L;

    public ServiceWrongLoginException() {
        super();
    }

    public ServiceWrongLoginException(String message) {
        super(message);
    }

    public ServiceWrongLoginException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongLoginException(Exception e) {
        super (e);
    }
}
