package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class PortManager {
    @Inject
    private PortFacade portFacade;

    public void addPort(Port port) {
        portFacade.create(port);
    }


    /**
     * Metoda, która zwraca wszystkie porty.
     *
     * @return lista portów
     */
    @RolesAllowed("getAllPorts")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Port> getAllPorts() {
        return portFacade.findAll();
    }

    /**
     * Metoda, która zwraca port o podanym id.
     *
     * @param portId id portu.
     * @return port
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getPortById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Port getPortById(Long portId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return portFacade.find(portId).orElseThrow(() -> new AppBaseException("nie ma tego portu"));
    }

    public void editPort(Long portId, Port portToEdit) throws AppBaseException {
        //portToEdit.setId(portId);
        portFacade.edit(portToEdit);
    }

    public void deactivatePort(Long portId) throws AppBaseException{
        Port portToDeactivate = getPortById(portId);
        portToDeactivate.setActive(false);
        portFacade.edit(portToDeactivate);
    }
}
