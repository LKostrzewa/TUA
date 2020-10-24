package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.EditPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.ListPortsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.PortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja interfejsu PortEndpoint.
 */
@Stateful(name = "PortEndpointImp")
@Named("PortEndpointImp")
@Interceptors(LoggerInterceptor.class)
public class PortEndpointImp implements Serializable, PortEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private @Named("PortManager") PortManager portManager;
    private @Named("Port") Port portEditEntity;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda, służy do dodawania nowych portów do bazy danych.
     *
     * @param newPortDto obiekt DTO z danymi nowego portu.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addPort")
    public void addPort(NewPortDto newPortDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                Port port = ObjectMapperUtils.map(newPortDto, Port.class);
                portManager.addPort(port);
                rollback = portManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która zapisuje wprowadzone zmiany w porcie.
     *
     * @param editPortDto obiekt DTO z danymi portu do edycji.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editPort")
    public void editPort(EditPortDto editPortDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                boolean nameChanged = true;
                if (portEditEntity.getName().equals(editPortDto.getName())) {
                    nameChanged = false;
                }
                portEditEntity.setName(editPortDto.getName());
                portEditEntity.setLake(editPortDto.getLake());
                portEditEntity.setNearestCity(editPortDto.getNearestCity());
                portEditEntity.setLong1(editPortDto.getLong1());
                portEditEntity.setLat(editPortDto.getLat());
                portManager.editPort(this.portEditEntity, nameChanged);
                rollback = portManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która deaktywuje port.
     *
     * @param portId id jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivatePort")
    public void deactivatePort(long portId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                portManager.deactivatePort(portId);
                rollback = portManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która zwraca wszystkie porty.
     *
     * @return lista portów
     */
    @RolesAllowed("getAllPorts")
    public List<ListPortsDto> getAllPorts() {
        return ObjectMapperUtils.mapAll(portManager.getAllPorts(), ListPortsDto.class);
    }
    /**
     * Metoda, która zwraca port o podanym id.
     *
     * @param portId id portu.
     * @return EditPortDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getPortById")
    public EditPortDto getEditPortById(Long portId) throws AppBaseException {
        this.portEditEntity = portManager.getPortById(portId);
        return ObjectMapperUtils.map(this.portEditEntity, EditPortDto.class);
    }

    /**
     * Metoda, która zwraca port o podanym id.
     *
     * @param portId id portu.
     * @return PortDetailsDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getPortById")
    public PortDetailsDto getPortById(Long portId) throws AppBaseException {
        Port port = portManager.getPortById(portId);
        return ObjectMapperUtils.map(port, PortDetailsDto.class);
    }

    /**
     * Metoda, która zwraca port o podanym id.
     *
     * @param portId id portu.
     * @return PortDetailsDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getPortById")
    public PortDetailsDto getPortDetailsById(Long portId) throws AppBaseException {
        this.portEditEntity = portManager.getPortById(portId);
        return ObjectMapperUtils.map(this.portEditEntity, PortDetailsDto.class);
    }
}
