package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

    public void init() throws AppBaseException{
        //this.userDetailsDto = userEndpoint.getUserDetailsDtoById(userId);
        this.userAccessLevelDto = userAccessLevelEndpoint.findUserAccessLevelById(userId);
        this.userDetailsDto = userAccessLevelDto.getUserDetailsDto();
    }
    /**
     * Metoda zwracająca łańcuch wszystkich poziomó dostępu konta
     *
     * @return połączony łańcuch poziomów dostępu konta
     */
    public String getAccessLevels() {
        String string = "";
        if (userAccessLevelDto.getAdmin().getLeft())
            string += "ADMINISTRATOR ";
        if (userAccessLevelDto.getManager().getLeft())
            string += "MANAGER ";
        if (userAccessLevelDto.getClient().getLeft())
            string += "CLIENT";
        return string;
    }

    public void lockAccount() throws AppBaseException{
        try {
            userDetailsDto.setLocked(true);
            userEndpoint.lockAccount(userId);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }

    }

    public void unlockAccount() throws AppBaseException {
        try{
            userDetailsDto.setLocked(false);
            userEndpoint.unlockAccount(userId);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }

    }


    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("blockAccount");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));

    }
}
