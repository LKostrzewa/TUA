package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

public class ValueNotUniqueException extends AppBaseException {

    public static String EMAIL_MESSAGE_KEY = "exception.emailNotUnique";
    public static String LOGIN_MESSAGE_KEY = "exception.loginNotUnique";

    private Object object;

    public ValueNotUniqueException(String message) {
        super(message);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static ValueNotUniqueException createEmailNotUniqueException(User user) {
        ValueNotUniqueException nue = new ValueNotUniqueException(EMAIL_MESSAGE_KEY);
        nue.setObject(user);
        return nue;
    }

    public static ValueNotUniqueException createLoginNotUniqueException(User user) {
        ValueNotUniqueException nue = new ValueNotUniqueException(LOGIN_MESSAGE_KEY);
        nue.setObject(user);
        return nue;
    }
}
