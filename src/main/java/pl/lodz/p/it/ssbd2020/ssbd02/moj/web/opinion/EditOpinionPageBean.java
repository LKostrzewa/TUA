package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ConversationScoped
public class EditOpinionPageBean implements Serializable {
    @Inject
    private OpinionEndpoint opinionEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private Long opinionId;
    private EditOpinionDto editOpinionDTO;

    public EditOpinionDto getEditOpinionDTO() {
        return editOpinionDTO;
    }

    public void setEditOpinionDTO(EditOpinionDto editOpinionDTO) {
        this.editOpinionDTO = editOpinionDTO;
    }

    public void init() throws AppBaseException{
        this.editOpinionDTO = opinionEndpoint.getOpinionById(opinionId);
    }

    public String editOpinion() {
        try{
            opinionEndpoint.editOpinion(editOpinionDTO);
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
