package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongNameException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 28811424593041L;

    public ServiceWrongNameException() {
        super();
    }

    public ServiceWrongNameException(String message) {
        super(message);
    }

    public ServiceWrongNameException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongNameException(Exception e) {
        super (e);
    }
}
