package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

/**
 * Wyjątek aplikacyjny występujący w przypadku pojawienia się PersistanceException
 */
public class AppPersistenceException extends AppBaseException {
    public static final String APE_MESSAGE_KEY = "exception.Persistence";

    private Object object;

    public AppPersistenceException(String message, Throwable cause) {
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
    public static AppPersistenceException createAppPersistenceException(Object object, Throwable cause) {
        AppPersistenceException ape = new AppPersistenceException(APE_MESSAGE_KEY, cause);
        ape.setObject(object);
        return ape;
    }
}
