package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;

public class EntityNotActiveException  extends AppBaseException{
    public static final String YACHT_MODEL_NOT_ACTIVE_KEY = "exception.yachtModelNotActive";
    public static final String YACHT_NOT_ACTIVE_KEY = "exception.yachtNotActive";
    public static final String PORT_NOT_ACTIVE_KEY = "exception.portNotActive";

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

    public static EntityNotActiveException createYachtModelNotActiveException(YachtModel yachtModel) {
        EntityNotActiveException ente = new EntityNotActiveException(YACHT_MODEL_NOT_ACTIVE_KEY);
        ente.setObject(yachtModel);
        return ente;
    }

    public static EntityNotActiveException createYachtNotActiveException(Yacht yacht) {
        EntityNotActiveException ente = new EntityNotActiveException(YACHT_NOT_ACTIVE_KEY);
        ente.setObject(yacht);
        return ente;
    }

    public static EntityNotActiveException createPortNotActiveException(Port port) {
        EntityNotActiveException ente = new EntityNotActiveException(PORT_NOT_ACTIVE_KEY);
        ente.setObject(port);
        return ente;
    }
}
