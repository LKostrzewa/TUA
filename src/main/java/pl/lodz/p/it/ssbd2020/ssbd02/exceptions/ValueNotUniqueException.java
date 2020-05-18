package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;

public class ValueNotUniqueException extends AppBaseException {

    public static final String EMAIL_MESSAGE_KEY = "exception.emailNotUnique";
    public static final String LOGIN_MESSAGE_KEY = "exception.loginNotUnique";
    public static final String YACHT_NAME_MESSAGE_KEY = "exception.yachtNameNotUnique";

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

    public static ValueNotUniqueException createYachtNameNotUniqueException(Yacht yacht) {
        ValueNotUniqueException nue = new ValueNotUniqueException(YACHT_NAME_MESSAGE_KEY);
        nue.setObject(yacht);
        return nue;
    }
}
