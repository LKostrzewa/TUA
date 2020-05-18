package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class YachtDetailsPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;

    private Long yachtId;
    private YachtDto yachtDto;

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }

    public YachtDto getYachtDto() {
        return yachtDto;
    }

    public void setYachtDto(YachtDto yachtDto) {
        this.yachtDto = yachtDto;
    }

    public void init() throws AppBaseException {
        this.yachtDto = yachtEndpoint.getYachtById(yachtId);
    }
}
