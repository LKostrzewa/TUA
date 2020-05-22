package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

/**
 * Wyjątek aplikacyjny występujący w przypadku pojawienia się OptimisticLockException
 */
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

    /**
     * Statyczna metoda do tworzenia wyjątku
     *
     * @param object obiekt, który spowodował wystąpienie wyjątku
     * @param cause przyczyna wajątku
     * @return obiekt wyjątku z odpowiednimi danymi
     */
    public static AppOptimisticLockException createAppOptimisticLockException(Object object, Throwable cause){
        AppOptimisticLockException ae = new AppOptimisticLockException(AOLE_MESSAGE_KEY, cause);
        ae.setObject(object);
        return ae;
    }

}
