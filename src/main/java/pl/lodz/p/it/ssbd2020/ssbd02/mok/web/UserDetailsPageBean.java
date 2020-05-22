package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa od obsługi widoku szczegółowych informacji konta innego użytkownika
 */
@Named
@ViewScoped
public class UserDetailsPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;

    @Inject
    private FacesContext facesContext;
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
     * Metoda inicjalizująca komponent
     */
    public void init() throws IOException {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");

        try {
            this.userAccessLevelDto = userAccessLevelEndpoint.findUserAccessLevelById(userId);
            this.userDetailsDto = userAccessLevelDto.getUserDetailsDto();
        }
        catch (AppBaseException e) {
            //do przetestowania / poprawnienia
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("listUsers.xhtml");
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
     * Metoda obsługująca wciśnięcie guzika do odblokowania użytkownika
     */
    public void unlockAccount() {
        try{
            userDetailsDto.setLocked(false);
            userEndpoint.unlockAccount(userId);
            displayMessage("unlockAccount");
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji
     */
    public void displayMessage(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
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
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));

    }
}
