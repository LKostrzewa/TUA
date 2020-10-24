package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangePasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku zmiany hasła użytkownika.
 */
@Named
@ViewScoped
public class ChangePasswordPageBean implements Serializable {
    @Inject
    private @Named("UserEndpoint") UserEndpoint userEndpoint;
    private ChangePasswordDto changePasswordDto;
    private Long userId;
    @Inject
    private @Named("FacesContext") FacesContext context;
    private ResourceBundle resourceBundle;

    public ChangePasswordDto getChangePasswordDto() {
        return changePasswordDto;
    }

    public void setChangePasswordDto(ChangePasswordDto changePasswordDto) {
        this.changePasswordDto = changePasswordDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() {
        this.changePasswordDto = new ChangePasswordDto();
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do zmiany swojego hasła.
     *
     * @return strona, na którą zostanie przekierowany użytkownik
     */
    public String changePassword() {
        try {
            userEndpoint.changeUserPassword(changePasswordDto, userId);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "userDetails.xhtml?faces-redirect=true?includeViewParams=true";
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
}
