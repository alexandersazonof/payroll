package by.etc.payroll.dao.exception;

import java.io.Serializable;

public class DAOException extends Exception implements Serializable {
    private static final long serialVersionUID = 18891428293121L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }

    public DAOException(Exception e) {
        super(e);
    }
}
