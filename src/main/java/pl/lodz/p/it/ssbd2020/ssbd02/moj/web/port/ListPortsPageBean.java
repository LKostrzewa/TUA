package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ListPortsPageBean {
    @Inject
    private PortEndpoint portEndpoint;
    private List<PortDetailsDto> ports;

    public List<PortDetailsDto> getPorts() {
        return ports;
    }

    public void setPorts(List<PortDetailsDto> ports) {
        this.ports = ports;
    }

    @PostConstruct
    private void init() {
        this.ports = portEndpoint.getAllPorts();
    }

}
