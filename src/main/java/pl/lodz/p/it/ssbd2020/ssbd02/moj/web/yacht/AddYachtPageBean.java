package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpointImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddYachtPageBean {
    @Inject
    private YachtEndpoint yachtEndpoint;
    private NewYachtDto newYachtDto;

    public NewYachtDto getNewYachtDto() {
        return newYachtDto;
    }

    public void setNewYachtDto(NewYachtDto newYachtDto) {
        this.newYachtDto = newYachtDto;
    }

    @PostConstruct
    public void init() {
        newYachtDto = new NewYachtDto();
    }

    public void addNewYacht() throws AppBaseException {
        newYachtDto.setYachtModelId((long) 1);
        yachtEndpoint.addYacht(newYachtDto);
    }
}
