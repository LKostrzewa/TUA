package pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class IncorrectPasswordException extends AppBaseException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
