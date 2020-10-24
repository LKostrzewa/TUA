package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi strony pojawiającej się po kliknięciu w link aktywacyjny.
 */
@Named
@RequestScoped
public class EmailPageBean {
    @Inject
    private @Named("UserEndpointImpl") UserEndpoint userEndpoint;
    private String key;
    private int valid;
    @Inject
    private @Named("FacesContext") FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private Boolean active;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        PropertyReader propertyReader = new PropertyReader();
        key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        valid = Integer.parseInt(propertyReader.getProperty("config", "email_valid_time"));
        ;
        try {
            active = userEndpoint.activateAccount(key);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
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
