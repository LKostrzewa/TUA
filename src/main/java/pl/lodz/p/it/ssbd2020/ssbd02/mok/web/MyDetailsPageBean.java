package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa od obsługi widoku szczegółowych informacji własnego konta.
 */
@Named
@ViewScoped
public class MyDetailsPageBean implements Serializable {
    @Inject
    private @Named("UserAccessLevelEndpointImpl") UserAccessLevelEndpoint userAccessLevelEndpoint;
    @Inject
    private @Named("FacesContext") FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private UserDetailsDto userDetailsDto;
    private UserAccessLevelDto userAccessLevelDto;
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

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        ADMIN_ACCESS_LEVEL = resourceBundle.getString("index.admin");
        MANAGER_ACCESS_LEVEL = resourceBundle.getString("index.manager");
        CLIENT_ACCESS_LEVEL = resourceBundle.getString("index.client");
        try {
            this.userAccessLevelDto = userAccessLevelEndpoint.findUserAccessLevelByLogin();
            this.userDetailsDto = userAccessLevelDto.getUserDetailsDto();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Prywatna metoda służąca do wyświetlania błędu użytkownikowi.
     *
     * @param message wiadomość która ma zostać wyświetlona użytkownikowi
     */
    private void displayError(String message) {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }

    /**
     * Metoda zwracająca łańcuch wszystkich poziomów dostępu konta.
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
