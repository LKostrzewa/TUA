package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import javax.ejb.ApplicationException;

public class RepeatedRollbackLimitException extends AppBaseException {
    public RepeatedRollbackLimitException(String message) {
        super(message);
    }
}
