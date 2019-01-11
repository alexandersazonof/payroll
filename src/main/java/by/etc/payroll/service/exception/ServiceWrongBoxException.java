package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongBoxException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 18891424593041L;

    public ServiceWrongBoxException() {
        super();
    }

    public ServiceWrongBoxException(String message) {
        super(message);
    }

    public ServiceWrongBoxException(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongBoxException(Exception e) {
        super (e);
    }


}
