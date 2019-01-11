package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongCountException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 12891424593041L;

    public ServiceWrongCountException() {
        super();
    }

    public ServiceWrongCountException(String message) {
        super(message);
    }

    public ServiceWrongCountException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongCountException(Exception e) {
        super (e);
    }
}
