package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;

public class AppNotFoundException extends AppBaseException{

    public static String ACCESS_LEVEL_MESSAGE_KEY = "exception.accessLevelDeleted";
    //public static String USER_ACCESS_LEVEL_MESSAGE_KEY = "exception.userNotFound";
    public static String USER_MESSAGE_KEY = "exception.userNotFound";
    private Object object;
    public AppNotFoundException(String message) {
        super(message);
    }
    public AppNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static AppNotFoundException createAccessLevelNotFoundException(AccessLevel accessLevel, Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(ACCESS_LEVEL_MESSAGE_KEY, cause);
        nfe.setObject(accessLevel);
        return nfe;
    }

    public static AppNotFoundException createUserNotFoundException(User user, Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(USER_MESSAGE_KEY, cause);
        nfe.setObject(user);
        return nfe;
    }
    /*
    public static AppNotFoundException createUserAccessLevelNotFoundException(UserAccessLevel userAccessLevel, Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(USER_ACCESS_LEVEL_MESSAGE_KEY, cause);
        nfe.setObject(userAccessLevel);
        return nfe;
    }*/
}
