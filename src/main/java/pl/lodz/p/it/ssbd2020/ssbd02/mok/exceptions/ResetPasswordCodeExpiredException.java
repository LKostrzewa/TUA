package pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class ResetPasswordCodeExpiredException extends AppBaseException {
    public ResetPasswordCodeExpiredException(String message) {
        super(message);
    }
}