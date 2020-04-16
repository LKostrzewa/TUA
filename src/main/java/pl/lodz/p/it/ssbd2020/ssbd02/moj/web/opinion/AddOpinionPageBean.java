package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddOpinionPageBean {

    @Inject
    private OpinionEndpoint opinionEndpoint;

    private NewOpinionDTO newOpinionDTO;

    @PostConstruct
    public void init(){
        newOpinionDTO = new NewOpinionDTO();
    }

    public String addOpinion(){
        opinionEndpoint.addOpinion(newOpinionDTO);
        return "client/rentalDetails.xhtml?faces-redirect=true";
    }

    public NewOpinionDTO getNewOpinionDTO() {
        return newOpinionDTO;
    }

    public void setNewOpinionDTO(NewOpinionDTO newOpinionDTO) {
        this.newOpinionDTO = newOpinionDTO;
    }
}
