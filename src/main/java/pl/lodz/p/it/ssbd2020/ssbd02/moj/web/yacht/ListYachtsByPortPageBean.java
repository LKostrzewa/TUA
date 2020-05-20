package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtPortEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ListYachtsByPortPageBean {
    @Inject
    private YachtPortEndpoint yachtPortEndpoint;
    private List<YachtDto> yachts;

    private Long portId;

    public List<YachtDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtDto> yachts) {
        this.yachts = yachts;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }


    public void init() throws AppBaseException {
        this.yachts = yachtPortEndpoint.getAllYachtsByPort(portId);
    }
}
