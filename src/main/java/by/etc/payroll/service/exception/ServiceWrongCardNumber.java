package by.etc.payroll.service.exception;

import java.io.Serializable;

public class ServiceWrongCardNumber extends ServiceException implements Serializable {

    private static final long serialVersionUID = 28891424593041L;

    public ServiceWrongCardNumber() {
        super();
    }

    public ServiceWrongCardNumber(String message) {
        super(message);
    }

    public ServiceWrongCardNumber(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongCardNumber(Exception e) {
        super (e);
    }


}
