package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class EditYachtModelPageBean implements Serializable {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private FacesContext facesContext;

    private ResourceBundle resourceBundle;
    private Long yachtModelId;
    private EditYachtModelDto editYachtModelDto;

    public Long getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(Long yachtModelId) {
        this.yachtModelId = yachtModelId;
    }

    public EditYachtModelDto getEditYachtModelDto() {
        return editYachtModelDto;
    }

    public void setEditYachtModelDto(EditYachtModelDto editYachtModelDto) {
        this.editYachtModelDto = editYachtModelDto;
    }

    /**
     * Metoda inicjalizująca komponent.
     *
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void init() throws IOException {
        try {
            this.editYachtModelDto = yachtModelEndpoint.getEditYachtModelDtoById(yachtModelId);
        } catch (AppBaseException e){
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "yachtModelDetials.xhtml");
        }
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do edycji modelu jachtu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String editYachtModel() {
        try {
            yachtModelEndpoint.editYachtModel(editYachtModelDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "yachtModelDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    private void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    private void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("users.editInfo");
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
