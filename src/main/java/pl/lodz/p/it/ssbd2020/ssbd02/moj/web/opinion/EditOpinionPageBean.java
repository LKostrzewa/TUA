package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku edycji opinii.
 */
@Named
@ViewScoped
public class EditOpinionPageBean implements Serializable {
    @Inject
    private OpinionEndpoint opinionEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private String rentalBusinessKey;
    private EditOpinionDto editOpinionDTO;

    public EditOpinionDto getEditOpinionDTO() {
        return editOpinionDTO;
    }

    public void setEditOpinionDTO(EditOpinionDto editOpinionDTO) {
        this.editOpinionDTO = editOpinionDTO;
    }

    public String getRentalBusinessKey() {
        return rentalBusinessKey;
    }

    public void setRentalBusinessKey(String rentalBusinessKey) {
        this.rentalBusinessKey = rentalBusinessKey;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws IOException {
        try {
            this.editOpinionDTO = opinionEndpoint.getOpinionByRentalBusinessKey(rentalBusinessKey);
        } catch (AppBaseException e){
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "client/rentalDetails.xhtml?faces-redirect=true");
        }
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
        return "client/rentalDetails.xhtml?faces-redirect=true?includeViewParams=true";
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
