package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangeOwnPasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku zmiany własnego hasła.
 */
@Named
@ViewScoped
public class ChangeOwnPasswordPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private FacesContext context;
    private ChangeOwnPasswordDto changeOwnPasswordDto;
    private ResourceBundle resourceBundle;

    public ChangeOwnPasswordDto getChangeOwnPasswordDto() {
        return changeOwnPasswordDto;
    }

    public void setChangeOwnPasswordDto(ChangeOwnPasswordDto changeOwnPasswordDto) {
        this.changeOwnPasswordDto = changeOwnPasswordDto;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        this.changeOwnPasswordDto = new ChangeOwnPasswordDto();
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do zmiany swojego hasła.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String changePassword() {
        try {
            userEndpoint.changeOwnPassword(changeOwnPasswordDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "account.xhtml?faces-redirect=true?includeViewParams=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    private void displayInit() {
        context.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    private void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("shared.password.changeSuccess");
        String head = resourceBundle.getString("success");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
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
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }

    public ResourceBundle getBundle() {
        return resourceBundle;
    }
}
