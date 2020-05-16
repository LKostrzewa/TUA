package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;

public class AppNotFoundException extends AppBaseException{

    public static final String ACCESS_LEVEL_MESSAGE_KEY = "exception.accessLevelDeleted";
    //public static String USER_ACCESS_LEVEL_MESSAGE_KEY = "exception.userNotFound";
    public static final String USER_MESSAGE_KEY = "exception.userNotFound";
    public static final String RENTAL_MESSAGE_KEY = "exception.rentalNotFound";
    public static final String EMAIL_MESSAGE_KEY = "exception.emailNotFound";
    private Class objectClass;
    public AppNotFoundException(String message) {
        super(message);
    }
    public AppNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public Class getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Class objectClass) {
        this.objectClass = objectClass;
    }

    public static AppNotFoundException createAccessLevelNotFoundException(Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(ACCESS_LEVEL_MESSAGE_KEY, cause);
        nfe.setObjectClass(AccessLevel.class);
        return nfe;
    }

    public static AppNotFoundException createUserNotFoundException(Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(USER_MESSAGE_KEY, cause);
        nfe.setObjectClass(User.class);
        return nfe;
    }

    public static AppNotFoundException createUserNotFoundException() {
        AppNotFoundException nfe = new AppNotFoundException(USER_MESSAGE_KEY);
        nfe.setObjectClass(User.class);
        return nfe;
    }

    public static AppNotFoundException createRentalNotFoundException(Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(RENTAL_MESSAGE_KEY, cause);
        nfe.setObjectClass(User.class);
        return nfe;
    }
    /*
    public static AppNotFoundException createUserAccessLevelNotFoundException(UserAccessLevel userAccessLevel, Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(USER_ACCESS_LEVEL_MESSAGE_KEY, cause);
        nfe.setObject(userAccessLevel);
        return nfe;
    }*/

    public static AppNotFoundException createEmailNotFoundException() {
        AppNotFoundException nfe = new AppNotFoundException(EMAIL_MESSAGE_KEY);
        nfe.setObjectClass(User.class);
        return nfe;
    }
}
