package pl.lodz.p.it.ssbd2020.ssbd02.moj.web;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtListDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListYachtPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    private List<YachtListDto> yachts;

    @PostConstruct
    private void init() {
        this.yachts = yachtEndpoint.getAllYachts();
    }

    public String deactivateYacht(Long yachtID) {
        yachtEndpoint.deactivateYacht(yachtID);
        return "listYachts";
    }

    public List<YachtListDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtListDto> yachts) {
        this.yachts = yachts;
    }
}
