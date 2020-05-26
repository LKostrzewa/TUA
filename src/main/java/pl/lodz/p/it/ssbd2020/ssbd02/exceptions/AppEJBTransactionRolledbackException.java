package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

/**
 * Wyjątek aplikacyjny występujący w przypadku pojawienia się EJBTransactionRolledbackException
 */
public class AppEJBTransactionRolledbackException extends AppBaseException{

    public static final String TRBE_MESSAGE_KEY = "exception.repeated.rollback";

    public AppEJBTransactionRolledbackException(String message) {
        super(message);
    }

    public AppEJBTransactionRolledbackException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Statyczna metoda do tworzenia wyjątku
     *
     * @param cause przyczyna wajątku
     * @return obiekt wyjątku z odpowiednimi danymi
     */
    public static AppEJBTransactionRolledbackException createAppEJBTransactionRolledbackException(Throwable cause) {
        return new AppEJBTransactionRolledbackException(TRBE_MESSAGE_KEY, cause);
    }

}
