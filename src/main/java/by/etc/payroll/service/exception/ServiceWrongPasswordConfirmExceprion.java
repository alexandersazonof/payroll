package by.etc.payroll.service.exception;

public class ServiceWrongPasswordConfirmExceprion extends ServiceException {

    private static final long serialVersionUID = 28891424593041L;

    public ServiceWrongPasswordConfirmExceprion() {
        super();
    }

    public ServiceWrongPasswordConfirmExceprion(String message) {
        super(message);
    }

    public ServiceWrongPasswordConfirmExceprion(String message, Exception e) {
        super(message, e);
    }

    public ServiceWrongPasswordConfirmExceprion(Exception e) {
        super (e);
    }


}
