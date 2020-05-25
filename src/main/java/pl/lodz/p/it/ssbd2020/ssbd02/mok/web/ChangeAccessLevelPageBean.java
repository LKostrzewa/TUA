package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;

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
 * Klasa do obsługi widoku zmiany poziomu dostępu
 */
@Named
@ViewScoped
public class ChangeAccessLevelPageBean implements Serializable {
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    private UserAccessLevelDto userDto;
    private Long userId;
    @Inject
    private FacesContext context;
    private ResourceBundle resourceBundle;

    public UserAccessLevelDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserAccessLevelDto userDto) {
        this.userDto = userDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Metoda inicjalizująca komponent
     */
    public void init() throws IOException {
        try {
            this.userDto = userAccessLevelEndpoint.findUserAccessLevelById(userId);
        }
        catch (AppBaseException e) {
            //do sprawdzenia/poprawy
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath()+"userDetails.xhtml?faces-redirect=true?includeViewParams=true");

        }
    }
    /**
     * Metoda obsługująca wciśnięcie guzika do zmiany poziomu dostępu
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String changeAccessLevel() {
        try {
            userAccessLevelEndpoint.editUserAccessLevels(userDto);
            displayMessage();
        }
        catch (AppBaseException e){
            //tutaj do potestowania bo cos nie halo chyba przy współbieżności
            displayError(e.getLocalizedMessage());
        }
        return "userDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    private void displayInit(){
        context.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji
     */
    private void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("users.changeAccessLevelInfo");
        String head = resourceBundle.getString("success");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie
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
