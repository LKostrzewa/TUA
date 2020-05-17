package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpointImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddYachtModelPageBean {
    @Inject
    private YachtModelEndpointImpl yachtModelEndpoint;
    private NewYachtModelDto newYachtModelDto;

    public NewYachtModelDto getNewYachtModelDto() {
        return newYachtModelDto;
    }

    public void setNewYachtModelDto(NewYachtModelDto newYachtModelDto) {
        this.newYachtModelDto = newYachtModelDto;
    }

    @PostConstruct
    public void init() {
        newYachtModelDto = new NewYachtModelDto();
    }

    public String addNewYachtModel() throws AppBaseException {
        yachtModelEndpoint.addYachtModel(newYachtModelDto);
        return "/manager/listYachtModels.xhtml?faces-redirect=true";
    }
}
