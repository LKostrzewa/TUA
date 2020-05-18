package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

public class RepeatedRollBackException extends AppBaseException {

    public static final String RRBE_MESSAGE_KEY = "exception.repeated.rollback";
    private Object object;

    public RepeatedRollBackException(String message) {
        super(message);
    }
    public RepeatedRollBackException(String message, Throwable cause) {
        super(message, cause);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static RepeatedRollBackException createRepeatedRollBackException(Object object) {
        RepeatedRollBackException rbe = new RepeatedRollBackException(RRBE_MESSAGE_KEY);
        rbe.setObject(object);
        return rbe;
    }
}
