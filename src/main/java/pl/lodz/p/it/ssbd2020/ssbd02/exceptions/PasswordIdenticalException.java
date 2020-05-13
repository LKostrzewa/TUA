package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

public class PasswordIdenticalException extends AppBaseException {

    public static final String PASSWORD_IDENTICAL_KEY = "exception.passwordIdentical";

    private User user;

    public PasswordIdenticalException(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static PasswordIdenticalException createPasswordIdenticalException(User user) {
        PasswordIdenticalException pie = new PasswordIdenticalException(PASSWORD_IDENTICAL_KEY);
        pie.setUser(user);
        return pie;
    }
}
