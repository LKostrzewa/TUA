package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

/**
 * Wyjątek aplikacyjny występujący w przypadku naruszenia reguł walidacji encji
 */
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

    /**
     * Statyczna metoda do tworzenia wyjątku
     *
     * @param object obiekt, który spowodował wystąpienie wyjątku
     * @param cause przyczyna wajątku
     * @return obiekt wyjątku z odpowiednimi danymi
     */
    public static AppConstraintViolationException createAppConstraintViolationException(Object object, Throwable cause) {
        AppConstraintViolationException acve = new AppConstraintViolationException(ACVE_MESSAGE_KEY, cause);
        acve.setObject(object);
        return acve;
    }
}
