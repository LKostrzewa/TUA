package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
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
 * Klasa od obsługi widoku szczegółowych informacji własnego konta
 */
@Named
@ViewScoped
public class MyDetailsPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    @Inject
    private FacesContext facesContext;
    private UserDetailsDto userDetailsDto;
    private UserAccessLevelDto userAccessLevelDto;
    private String userLogin;

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public UserAccessLevelDto getUserAccessLevelDto() {
        return userAccessLevelDto;
    }

    public void setUserAccessLevelDto(UserAccessLevelDto userAccessLevelDto) {
        this.userAccessLevelDto = userAccessLevelDto;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @PostConstruct
    public void init()  {
        userLogin = facesContext.getExternalContext().getRemoteUser();
        try {
            this.userAccessLevelDto = userAccessLevelEndpoint.findUserAccessLevelByLogin();
            this.userDetailsDto = userAccessLevelDto.getUserDetailsDto();
        } catch (AppBaseException e) {
            //redirect ?
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Prywatna metoda służąca do wyświetlania błędu użytkownikowi
     *
     * @param message wiadomość która ma zostać wyświetlona użytkownikowi
     */
    private void displayError(String message) {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));

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
}
