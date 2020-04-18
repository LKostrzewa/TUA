package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;


import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddYachtModelPageBean {

    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private NewYachtModelDTO newYachtModelDTO;

    @PostConstruct
    public void init() {
        newYachtModelDTO = new NewYachtModelDTO();
    }

    public void addNewYachtModel() {
        yachtModelEndpoint.addYachtModel(newYachtModelDTO);
    }


    public NewYachtModelDTO getNewYachtModelDTO() {
        return newYachtModelDTO;
    }

    public void setNewYachtModelDTO(NewYachtModelDTO newYachtModelDTO) {
        this.newYachtModelDTO = newYachtModelDTO;
    }
}
