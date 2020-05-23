package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import javax.ejb.ApplicationException;

/**
 * Bazowa klas wyjątku aplikacyjnego
 */
@ApplicationException(rollback = true)
public class AppBaseException extends Exception {
    public AppBaseException(String message) {
        super(message);
    }
    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
