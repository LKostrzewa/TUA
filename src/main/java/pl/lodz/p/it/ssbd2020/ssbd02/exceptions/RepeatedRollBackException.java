package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

/**
 * Wyjątek aplikacyjny występujący przy przekroczeniu limitu
 * ilości wycofanych transakcji
 */
public class RepeatedRollBackException extends AppBaseException {

    public static final String RRBE_MESSAGE_KEY = "exception.repeated.rollback";

    public RepeatedRollBackException(String message) {
        super(message);
    }
    public RepeatedRollBackException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Statyczna metoda do tworzenia wyjątku
     *
     * @return obiekt wyjątku z odpowiednimi danymi
     */
    public static RepeatedRollBackException createRepeatedRollBackException() {
        return new RepeatedRollBackException(RRBE_MESSAGE_KEY);
    }
}
