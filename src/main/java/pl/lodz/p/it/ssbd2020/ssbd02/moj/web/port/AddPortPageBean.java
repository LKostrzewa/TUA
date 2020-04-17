package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;


import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddPortPageBean {
    @Inject
    private PortEndpoint portEndpoint;
    private NewPortDto newPortDto;

    public NewPortDto getNewPortDto() {
        return newPortDto;
    }

    public void setNewPortDto(NewPortDto newPortDto) {
        this.newPortDto = newPortDto;
    }

    @PostConstruct
    public void init() {
        newPortDto = new NewPortDto();
    }

    public String addPort() {
        portEndpoint.addPort(newPortDto);
        return "/manager/listPorts.xhtml?faces-redirect=true";
    }
}
