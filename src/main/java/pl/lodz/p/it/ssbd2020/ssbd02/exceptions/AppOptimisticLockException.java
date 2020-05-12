package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

public class AppOptimisticLockException extends AppBaseException {

    public static final String AOLE_MESSAGE_KEY = "exception.optimisticLock";
    private Object object;

    public AppOptimisticLockException(String message) {
        super(message);
    }

    public AppOptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static AppOptimisticLockException createAppOptimisticLockException(Object object, Throwable cause){
        AppOptimisticLockException ae = new AppOptimisticLockException(AOLE_MESSAGE_KEY, cause);
        ae.setObject(object);
        return ae;
    }

}
