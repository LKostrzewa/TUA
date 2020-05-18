package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.EditPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.PortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class PortEndpointImp implements Serializable,PortEndpoint {
    @Inject
    private PortManager portManager;

    private Port portEditEntity;

    /**
     * Metoda, służy do dodawania nowych portów do bazy danych.
     *
     * @param newPortDto obiekt DTO z danymi nowego portu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addPort")
    public void addPort(NewPortDto newPortDto) throws AppBaseException {
        Port port = ObjectMapperUtils.map(newPortDto, Port.class);
        portManager.addPort(port);
    }

    /**
     * Metoda, która zapisuje wprowadzone zmiany w porcie.
     *
     * @param editPortDto obiekt DTO z danymi portu do edycji.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editPort")
    public void editPort(EditPortDto editPortDto) throws AppBaseException {
        Port portToEdit = ObjectMapperUtils.map(editPortDto, Port.class);
        portManager.editPort(portToEdit);
    }

    /**
     * Metoda, która deaktywuje port.
     *
     * @param portId id jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivatePort")
    public void deactivatePort(long portId) throws AppBaseException {
        portManager.deactivatePort(portEditEntity);
    }

    /**
     * Metoda, która zwraca wszystkie porty.
     *
     * @return lista portów
     */
    @RolesAllowed("getAllPorts")
    public List<PortDetailsDto> getAllPorts() {
        return ObjectMapperUtils.mapAll(portManager.getAllPorts(), PortDetailsDto.class);
    }

    public PortDetailsDto getPortById(Long portId) throws AppBaseException{
        Port port = portManager.getPortById(portId);
        return ObjectMapperUtils.map(port, PortDetailsDto.class);
    }
    /**
     * Metoda, która zwraca port o podanym id.
     *
     * @param portId id portu.
     * @return PortDetailsDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getPortById")
    public PortDetailsDto getPortDetailsById(Long portId) throws AppBaseException {
        this.portEditEntity = portManager.getPortById(portId);
        return ObjectMapperUtils.map(this.portEditEntity,PortDetailsDto.class);
    }
}
