package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

public class UserDataChangedException extends AppBaseException {


    private static final String BUSY_LOCK_KEY ="exception.lock";
    private static final String BUSY_UNLOCK_KEY = "exception.unlock";

    public UserDataChangedException(String message) {
        super(message);
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserDataChangedException lockValueChanged(User user) {
        UserDataChangedException udce = new UserDataChangedException(BUSY_LOCK_KEY);
        udce.setUser(user);
        return udce;
    }

    public static UserDataChangedException unlockValueChanged(User user) {
        UserDataChangedException udce = new UserDataChangedException(BUSY_UNLOCK_KEY);
        udce.setUser(user);
        return udce;
    }
}
