package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.EditPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.PortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

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

    public void addPort(NewPortDto newPortDto) {
        Port port = ObjectMapperUtils.map(newPortDto, Port.class);
        portManager.addPort(port);
    }

    public List<PortDto> getAllPorts() {
        return ObjectMapperUtils.mapAll(portManager.getAllPorts(), PortDto.class);
    }

    public PortDto getPortById(Long portId) throws AppBaseException{
        Port port = portManager.getPortById(portId);
        return ObjectMapperUtils.map(port, PortDto.class);
    }

    public void editPort(Long portId, EditPortDto editPortDto) throws AppBaseException {
        Port portToEdit = ObjectMapperUtils.map(editPortDto, Port.class);
        portManager.editPort(portId, portToEdit);
    }

    public void deactivatePort(Long portId) throws AppBaseException {
        portManager.deactivatePort(portId);
    }
}
