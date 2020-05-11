package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class ResetPasswordCodeExpiredException extends AppBaseException {

    public static String RESET_CODE_EXPIRED_CONSTRAINT_KEY = "exception.codeExpired";

    private User user;

    public ResetPasswordCodeExpiredException(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ResetPasswordCodeExpiredException createPasswordExceptionWithCodeExpiredConstraint(User user) {
        ResetPasswordCodeExpiredException rpce = new ResetPasswordCodeExpiredException(RESET_CODE_EXPIRED_CONSTRAINT_KEY);
        rpce.setUser(user);
        return rpce;
    }
}