package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku edycji jachtu.
 */
@Named
@ViewScoped
public class EditYachtPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private Long yachtId;
    private EditYachtDto editYachtDto;

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }

    public EditYachtDto getEditYachtDto() {
        return editYachtDto;
    }

    public void setEditYachtDto(EditYachtDto editYachtDto) {
        this.editYachtDto = editYachtDto;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws AppBaseException{
        this.editYachtDto = yachtEndpoint.getEditYachtDtoById(yachtId);
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do edycji jachtu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String editYacht() {
        try {
            yachtEndpoint.editYacht(editYachtDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "yachtDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("yacht.editInfo");
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
