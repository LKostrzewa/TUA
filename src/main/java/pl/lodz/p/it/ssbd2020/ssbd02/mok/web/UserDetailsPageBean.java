package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

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
 * Klasa od obsługi widoku szczegółowych informacji konta innego użytkownika.
 */
@Named
@ViewScoped
public class UserDetailsPageBean implements Serializable {
    @Inject
    private @Named("UserEndpoint") UserEndpoint userEndpoint;
    @Inject
    private @Named("UserAccessLevelEndpoint") UserAccessLevelEndpoint userAccessLevelEndpoint;
    @Inject
    private @Named("FacesContext") FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private UserDetailsDto userDetailsDto;
    private UserAccessLevelDto userAccessLevelDto;
    private Long userId;
    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;

    public UserAccessLevelDto getUserAccessLevelDto() {
        return userAccessLevelDto;
    }

    public void setUserAccessLevelDto(UserAccessLevelDto userAccessLevelDto) {
        this.userAccessLevelDto = userAccessLevelDto;
    }

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Metoda inicjalizująca komponent.
     *
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void init() throws IOException {
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        ADMIN_ACCESS_LEVEL = resourceBundle.getString("index.admin");
        MANAGER_ACCESS_LEVEL = resourceBundle.getString("index.manager");
        CLIENT_ACCESS_LEVEL = resourceBundle.getString("index.client");

        try {
            this.userAccessLevelDto = userAccessLevelEndpoint.findUserAccessLevelById(userId);
            this.userDetailsDto = userAccessLevelDto.getUserDetailsDto();
        } catch (AppBaseException e) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "listUsers.xhtml");
        }
    }

    /**
     * Metoda zwracająca łańcuch wszystkich poziomów dostępu konta
     *
     * @return połączony łańcuch poziomów dostępu konta
     */
    public String getAccessLevels() {
        String string = "";
        if (userAccessLevelDto.getAdmin().getLeft())
            string += ADMIN_ACCESS_LEVEL + " ";
        if (userAccessLevelDto.getManager().getLeft())
            string += MANAGER_ACCESS_LEVEL + " ";
        if (userAccessLevelDto.getClient().getLeft())
            string += CLIENT_ACCESS_LEVEL;
        return string;
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do zablokowania użytkownika
     */
    public void lockAccount() {
        try {
            userDetailsDto.setLocked(true);
            userEndpoint.lockAccount(userId);
            displayMessage("blockAccount");
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do odblokowania użytkownika.
     */
    public void unlockAccount() {
        try {
            userDetailsDto.setLocked(false);
            userEndpoint.unlockAccount(userId);
            displayMessage("unlockAccount");
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     *
     * @param message wiadomość do wyświetlenia
     */
    public void displayMessage(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
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
