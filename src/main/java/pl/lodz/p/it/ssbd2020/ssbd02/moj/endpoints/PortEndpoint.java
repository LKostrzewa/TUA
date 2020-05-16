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
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class PortEndpoint implements Serializable {
    @Inject
    private PortManager portManager;

    public void addPort(NewPortDto newPortDto) throws AppBaseException {
        Port port = ObjectMapperUtils.map(newPortDto, Port.class);
        portManager.addPort(port);
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

    public void editPort(Long portId, EditPortDto editPortDto) throws AppBaseException {
        Port portToEdit = ObjectMapperUtils.map(editPortDto, Port.class);
        portManager.editPort(portId, portToEdit);
    }

    public void deactivatePort(Long portId) throws AppBaseException {
        portManager.deactivatePort(portId);
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
        return ObjectMapperUtils.map(portManager.getPortById(portId),PortDetailsDto.class);
    }
}
