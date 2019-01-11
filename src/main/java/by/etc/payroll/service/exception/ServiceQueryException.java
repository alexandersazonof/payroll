package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceQueryException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 128891424593041L;

    public ServiceQueryException() {
        super();
    }

    public ServiceQueryException(String message) {
        super(message);
    }

    public ServiceQueryException(String message, Exception e) {
        super(message, e);
    }

    public ServiceQueryException(Exception e) {
        super (e);
    }

}
