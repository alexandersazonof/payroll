package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceUnauthorizedAccessException extends ServiceException implements Serializable {

    private static final long serialVersionUID = 183311124593041L;

    public ServiceUnauthorizedAccessException() {
        super();
    }

    public ServiceUnauthorizedAccessException(String message) {
        super(message);
    }

    public ServiceUnauthorizedAccessException(String message, Exception e) {
        super(message, e);
    }

    public ServiceUnauthorizedAccessException(Exception e) {
        super (e);
    }
}
