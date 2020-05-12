package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

public class IncorrectPasswordException extends AppBaseException {

    public static final String INCORRECT_CONSTRAINT_KEY = "exception.incorrectPassword";

    private User user;

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static IncorrectPasswordException createIncorrectPasswordException(User user) {
        IncorrectPasswordException ipe = new IncorrectPasswordException(INCORRECT_CONSTRAINT_KEY);
        ipe.setUser(user);
        return ipe;
    }
}
