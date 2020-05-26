package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.ValueNotUniqueException;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class PortManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private PortFacade portFacade;

    /**
     * Metoda, służy do dodawania nowych portów do bazy danych.
     *
     * @param port encja portu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addPort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addPort(Port port) throws AppBaseException {
        Port portToAdd = new Port(port.getName(),port.getLake(),port.getNearestCity(),port.getLong1(),port.getLat());

        if(portFacade.existByName(portToAdd.getName())){
            throw ValueNotUniqueException.createPortNameNotUniqueException(portToAdd);
        }
        portToAdd.setActive(true);
        portFacade.create(portToAdd);
    }

    /**
     * Metoda, która edytuje zmiany wprowadzone w encji portu.
     *
     * @param portToEdit edytowany port.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editPort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editPort(Port portToEdit) throws AppBaseException {
        /*if(portFacade.existByName(portToEdit.getName())){
            throw ValueNotUniqueException.createPortNameNotUniqueException(portToEdit);
        }*/
        //TODO: zdecydowac czy można zmieniać nazwę
        portFacade.edit(portToEdit);
    }

    /**
     * Metoda, która deaktywuje dany port.
     *
     * @param portId identyfikator portu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivatePort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deactivatePort(long portId) throws AppBaseException{
        Port portToDeactivate = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        portToDeactivate.setActive(false);
        portFacade.edit(portToDeactivate);
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
        return portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
    }
}
