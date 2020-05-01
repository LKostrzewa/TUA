package pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class EmailNotUniqueException extends AppBaseException {
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
