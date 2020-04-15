package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;



import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.UpdatePortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.PortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.PortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
        return portManager.getAllPorts().stream().map(n -> ObjectMapperUtils.map(n, PortDto.class)).collect(Collectors.toList());
    }

    public PortDto getPortById(Long portId) {
        Port port = portManager.getPortById(portId);
        return ObjectMapperUtils.map(port, PortDto.class);
    }

    public void updatePort(Long portId, UpdatePortDto updatePortDto) {
        Port portToUpdate = ObjectMapperUtils.map(updatePortDto, Port.class);
        portManager.updatePort(portId, portToUpdate);
    }

    public void deactivatePort(Long portId) {
        portManager.deactivatePort(portId);
    }
}
