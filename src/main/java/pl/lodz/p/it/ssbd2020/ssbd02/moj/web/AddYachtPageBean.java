package pl.lodz.p.it.ssbd2020.ssbd02.moj.web;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

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

    @PostConstruct
    public void init() {
        newYachtDto = new NewYachtDto();
    }

    public void addNewYacht() {
        newYachtDto.setYachtModelId((long) 1);
        yachtEndpoint.addYacht(newYachtDto);
    }

    public NewYachtDto getNewYachtDto() {
        return newYachtDto;
    }

    public void setNewYachtDto(NewYachtDto newYachtDto) {
        this.newYachtDto = newYachtDto;
    }
}
