package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongEmailException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 183311424593041L;

    public ServiceWrongEmailException() {
        super();
    }

    public ServiceWrongEmailException(String message) {
        super(message);
    }

    public ServiceWrongEmailException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongEmailException(Exception e) {
        super (e);
    }
}
