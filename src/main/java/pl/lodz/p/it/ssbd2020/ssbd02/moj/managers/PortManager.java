package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Klasa menadżera do obsługi operacji związanych z portami.
 */
@Stateful(name = "PortManager")
@Named("PortManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class PortManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private @Named("PortFacade") PortFacade portFacade;

    /**
     * Metoda, służy do dodawania nowych portów do bazy danych.
     *
     * @param port encja portu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addPort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addPort(Port port) throws AppBaseException {
        try {
            Port portToAdd = new Port(port.getName(), port.getLake(), port.getNearestCity(), port.getLong1(), port.getLat());

            if (portFacade.existByName(portToAdd.getName())) {
                throw ValueNotUniqueException.createPortNameNotUniqueException(portToAdd);
            }
            portToAdd.setActive(true);
            portFacade.create(portToAdd);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która edytuje zmiany wprowadzone w encji portu
     *
     * @param portEntity  edytowany port.
     * @param nameChanged wartość logiczna informująca o zmianie unikalnej nazwy portu.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editPort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editPort(Port portEntity, boolean nameChanged) throws AppBaseException {
        try {
            if (!portEntity.isActive()) {
                throw EntityNotActiveException.createPortNotActiveException(portEntity);
            }

            if (nameChanged && portFacade.existByName(portEntity.getName())) {
                throw ValueNotUniqueException.createPortNameNotUniqueException(portEntity);
            }
            portFacade.edit(portEntity);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która deaktywuje dany port.
     *
     * @param portId identyfikator portu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivatePort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deactivatePort(long portId) throws AppBaseException {
        try {
            Port portToDeactivate = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
            portToDeactivate.setActive(false);
            portFacade.edit(portToDeactivate);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
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
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getPortById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Port getPortById(Long portId) throws AppBaseException {
        return portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
    }
}
