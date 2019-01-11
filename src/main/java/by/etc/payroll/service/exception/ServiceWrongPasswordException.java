package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongPasswordException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 12811424593041L;

    public ServiceWrongPasswordException() {
        super();
    }

    public ServiceWrongPasswordException(String message) {
        super(message);
    }

    public ServiceWrongPasswordException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongPasswordException(Exception e) {
        super (e);
    }
}
