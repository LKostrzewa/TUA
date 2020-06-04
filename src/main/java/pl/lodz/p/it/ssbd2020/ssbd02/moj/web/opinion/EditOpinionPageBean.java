package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku edycji opinii.
 */
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

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws AppBaseException {
        this.editOpinionDTO = opinionEndpoint.getOpinionById(opinionId);
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do edycji opinii.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String editOpinion() {
        try {
            opinionEndpoint.editOpinion(editOpinionDTO);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "client/rentalDetails.xhtml?faces-redirect=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("opinion.addInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie.
     *
     * @param message wiadomość do wyświetlenia
     */
    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }
}
