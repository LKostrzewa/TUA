package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

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

    public List<YachtDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtDto> yachts) {
        this.yachts = yachts;
    }

    //nwm czy tak mozna
    @PostConstruct
    private void init(Long id) {
        this.yachts = yachtPortEndpoint.getAllYachtsByPort(id);
    }
}
