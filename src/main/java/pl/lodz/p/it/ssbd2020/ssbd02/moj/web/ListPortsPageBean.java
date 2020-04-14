package pl.lodz.p.it.ssbd2020.ssbd02.moj.web;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.PortDto;
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

    private List<PortDto> ports;

    @PostConstruct
    private void init(){
        this.ports = portEndpoint.getAllPorts();
    }

    public List<PortDto> getPorts() {
        return ports;
    }

    public void setPorts(List<PortDto> ports) {
        this.ports = ports;
    }

}
