package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.EditPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

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
public class EditPortPageBean implements Serializable {
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private Long portId;
    private EditPortDto editPortDto;

    /**
     * Metoda inicjalizująca komponent.
     *
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void init() throws IOException {
        try{
            this.editPortDto = portEndpoint.getEditPortById(portId);
        } catch (AppBaseException e){
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "portDetails.xhtml");
        }
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do edycji portu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String editPort() {
        try {
            portEndpoint.editPort(editPortDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "portDetails.xhtml?faces-redirect=true?includeViewParams=true";
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
        String msg = resourceBundle.getString("port.editInfo");
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

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public EditPortDto getEditPortDto() {
        return editPortDto;
    }

    public void setEditPortDto(EditPortDto editPortDto) {
        this.editPortDto = editPortDto;
    }
}
