package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

public class EntityNotActiveException  extends AppBaseException{
    public static final String YACHT_MODEL_NOT_ACTIVE_KEY = "exception.yachtModelNotActive";

    private Object object;
    public EntityNotActiveException(String message) {
        super(message);
    }
    public EntityNotActiveException(String message, Throwable cause){
        super(message, cause);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static EntityNotActiveException createYachtModelNotActiveException(Object object) {
        EntityNotActiveException ente = new EntityNotActiveException(YACHT_MODEL_NOT_ACTIVE_KEY);
        ente.setObject(object);
        return ente;
    }
}
