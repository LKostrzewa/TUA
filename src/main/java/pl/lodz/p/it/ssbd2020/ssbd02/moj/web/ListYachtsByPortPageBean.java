package pl.lodz.p.it.ssbd2020.ssbd02.moj.web;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.PortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;
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

    //nwm czy tak mozna
    @PostConstruct
    private void init(Long id){
        this.yachts = yachtPortEndpoint.getAllYachtsByPort(id);
    }

    public List<YachtDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtDto> yachts) {
        this.yachts = yachts;
    }
}
