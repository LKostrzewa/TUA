package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;

public class AppNotFoundException extends AppBaseException{

    public static String ACCESS_LEVEL_MESSAGE_KEY = "exception.accessLevelDeleted";
    //public static String USER_ACCESS_LEVEL_MESSAGE_KEY = "exception.userNotFound";
    public static String USER_MESSAGE_KEY = "exception.userNotFound";
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
    /*
    public static AppNotFoundException createUserAccessLevelNotFoundException(UserAccessLevel userAccessLevel, Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(USER_ACCESS_LEVEL_MESSAGE_KEY, cause);
        nfe.setObject(userAccessLevel);
        return nfe;
    }*/
}
