package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa od obsługi widoku szczegółowych informacji własnego konta
 */
@Named
@ViewScoped
public class MyDetailsPageBean implements Serializable {
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    @Inject
    private FacesContext facesContext;
    private UserDetailsDto userDetailsDto;
    private UserAccessLevelDto userAccessLevelDto;
    private String userLogin;

    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;

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

    /*public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }*/

    /**
     * Metoda inicjalizująca komponent
     */
    @PostConstruct //bez PostConstruct jest nulllllll a z postConstruct nie moze byc IOException
    public void init() /*throws IOException*/ {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");

        //userLogin = facesContext.getExternalContext().getRemoteUser();
        try {
            this.userAccessLevelDto = userAccessLevelEndpoint.findUserAccessLevelByLogin();
            this.userDetailsDto = userAccessLevelDto.getUserDetailsDto();
        } catch (AppBaseException e) {
            //do przetestowania / poprawienia
            displayError(e.getLocalizedMessage());
            /*FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("listUsers.xhtml");*/
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
}
