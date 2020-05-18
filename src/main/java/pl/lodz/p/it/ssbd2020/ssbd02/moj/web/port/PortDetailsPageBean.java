package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class PortDetailsPageBean implements Serializable {

    @Inject
    private PortEndpoint portEndpoint;

    private PortDetailsDto portDetails;
    private Long portId;

    public PortDetailsDto getPortDetails() {
        return portDetails;
    }

    public void setPortDetails(PortDetailsDto portDetails) {
        this.portDetails = portDetails;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public void init() throws AppBaseException {
        this.portDetails = portEndpoint.getPortDetailsById(this.portId);
    }

}
