package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceException extends Exception  implements Serializable {
    private static final long serialVersionUID = 18891424593041L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }

    public ServiceException(Exception e) {
        super (e);
    }


}
