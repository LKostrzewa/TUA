package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.*;

public class AppNotFoundException extends AppBaseException{

    public static final String ACCESS_LEVEL_MESSAGE_KEY = "exception.accessLevelDeleted";
    //public static String USER_ACCESS_LEVEL_MESSAGE_KEY = "exception.userNotFound";
    public static final String USER_MESSAGE_KEY = "exception.userNotFound";
    public static final String RENTAL_MESSAGE_KEY = "exception.rentalNotFound";
    public static final String EMAIL_MESSAGE_KEY = "exception.emailNotFound";
    public static final String PORT_MESSAGE_KEY = "exception.portNotFound";
    public static final String YACHT_MESSAGE_KEY = "exception.yachtNotFound";
    public static final String YACHT_MODEL_MESSAGE_KEY = "exception.yachtModelNotFound";
    public static final String OPINION_MESSAGE_KEY = "exception.opinionNotFound";

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

    public static AppNotFoundException yachtModelNotFoundException() {
        AppNotFoundException nfe = new AppNotFoundException(YACHT_MODEL_MESSAGE_KEY);
        nfe.setObjectClass(User.class);
        return nfe;
    }

    public static AppNotFoundException createRentalNotFoundException(Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(RENTAL_MESSAGE_KEY, cause);
        nfe.setObjectClass(Rental.class);
        return nfe;
    }

    public static AppNotFoundException createRentalStatusNotFoundException(Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(RENTAL_MESSAGE_KEY, cause);
        nfe.setObjectClass(RentalStatus.class);
        return nfe;
    }

    public static AppNotFoundException createPortNotFoundException() {
        AppNotFoundException nfe = new AppNotFoundException(PORT_MESSAGE_KEY);
        nfe.setObjectClass(Port.class);
        return nfe;
    }

    public static AppNotFoundException createYachtNotFoundException() {
        AppNotFoundException nfe = new AppNotFoundException(YACHT_MESSAGE_KEY);
        nfe.setObjectClass(Yacht.class);
        return nfe;
    }

    public static AppNotFoundException createYachtNotFoundException(Throwable cause) {
        AppNotFoundException nfe = new AppNotFoundException(YACHT_MESSAGE_KEY, cause);
        nfe.setObjectClass(Yacht.class);
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

    public static AppNotFoundException createOpinionNotFoundException() {
        AppNotFoundException nfe = new AppNotFoundException(OPINION_MESSAGE_KEY);
        nfe.setObjectClass(Opinion.class);
        return nfe;
    }
}
