package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class AddOpinionPageBean {
    @Inject
    private OpinionEndpoint opinionEndpoint;
    @Inject
    private FacesContext facesContext;
    private NewOpinionDto newOpinionDTO;
    private ResourceBundle resourceBundle;

    public NewOpinionDto getNewOpinionDTO() {
        return newOpinionDTO;
    }

    public void setNewOpinionDTO(NewOpinionDto newOpinionDTO) {
        this.newOpinionDTO = newOpinionDTO;
    }

    @PostConstruct
    public void init() {
        this.newOpinionDTO = new NewOpinionDto();
    }

    public String addOpinion() {
        try {
            opinionEndpoint.addOpinion(newOpinionDTO);
            displayMessage();
        } catch (AppBaseException e){
            displayError(e.getLocalizedMessage());
        }
        return "client/rentalDetails.xhtml?faces-redirect=true";
    }
    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("opinion.addInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }

}
