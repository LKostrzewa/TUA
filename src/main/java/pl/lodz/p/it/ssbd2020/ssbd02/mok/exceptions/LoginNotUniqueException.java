package pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

public class LoginNotUniqueException extends AppBaseException {
    public static final String LOGIN_MSG_KEY = "exception.loginNotUnique";
    private User user;
    public LoginNotUniqueException(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static LoginNotUniqueException createLoginNotUniqueException(User user) {
        LoginNotUniqueException le = new LoginNotUniqueException(LOGIN_MSG_KEY);
        //le.setUser(user);
        return le;
    }
}
