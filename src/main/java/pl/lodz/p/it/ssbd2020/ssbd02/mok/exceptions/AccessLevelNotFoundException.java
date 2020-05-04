package pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class AccessLevelNotFoundException extends AppBaseException {
    public AccessLevelNotFoundException(String message) {
        super(message);
    }
}
