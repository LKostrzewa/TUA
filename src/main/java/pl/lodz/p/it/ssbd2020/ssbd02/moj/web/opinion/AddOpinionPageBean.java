package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
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
    private NewOpinionDto newOpinionDTO;

    public NewOpinionDto getNewOpinionDTO() {
        return newOpinionDTO;
    }

    public void setNewOpinionDTO(NewOpinionDto newOpinionDTO) {
        this.newOpinionDTO = newOpinionDTO;
    }

    @PostConstruct
    public void init() {
        newOpinionDTO = new NewOpinionDto();
    }

    public String addOpinion() {
        opinionEndpoint.addOpinion(newOpinionDTO);
        return "client/rentalDetails.xhtml?faces-redirect=true";
    }

}
