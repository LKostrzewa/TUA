package pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class UserNotFoundException extends AppBaseException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
