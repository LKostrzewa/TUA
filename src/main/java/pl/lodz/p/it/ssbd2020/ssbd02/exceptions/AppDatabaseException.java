package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

public class AppDatabaseException extends AppBaseException {

    public static final String ADE_MESSAGE_KEY = "exception.databaseException";
    private Object object;
    public AppDatabaseException(String message) {
        super(message);
    }

    public AppDatabaseException(String message, Throwable cause) {
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
     * @param cause przyczyna wajątku
     * @return obiekt wyjątku z odpowiednimi danymi
     */
    public static AppDatabaseException createAppDatabaseException(Object object, Throwable cause){
        AppDatabaseException ad = new AppDatabaseException(ADE_MESSAGE_KEY, cause);
        ad.setObject(object);
        return ad;
    }
}
