package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceBlockAccountException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 188591424593041L;

    public ServiceBlockAccountException() {
        super();
    }

    public ServiceBlockAccountException(String message) {
        super(message);
    }

    public ServiceBlockAccountException(String message, Exception e) {
        super(message, e);
    }

    public ServiceBlockAccountException(Exception e) {
        super (e);
    }
}
