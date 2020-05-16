package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

public class AppConstraintViolationException extends AppBaseException {
    public static final String ACVE_MESSAGE_KEY = "exception.ConstraintViolation";

    private Object object;

    public AppConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static AppConstraintViolationException createAppConstraintViolationException(Object object, Throwable cause) {
        AppConstraintViolationException acve = new AppConstraintViolationException(ACVE_MESSAGE_KEY, cause);
        acve.setObject(object);
        return acve;
    }
}
