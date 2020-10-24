package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania użytkownika.
 */
@Named
@RequestScoped
public class AddUserPageBean implements Serializable {
    @Inject
    private @Named("UserEndpoint") UserEndpoint userEndpoint;
    private AddUserDto addUserDto;
    @Inject
    private @Named("FacesContext") FacesContext context;
    private ResourceBundle resourceBundle;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        addUserDto = new AddUserDto();
    }

    public AddUserDto getAddUserDto() {
        return addUserDto;
    }

    public void setAddUserDto(AddUserDto addUserDto) {
        this.addUserDto = addUserDto;
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do dodania nowego użytkownika.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String addUser() {
        try {
            userEndpoint.addNewUser(addUserDto);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
            return "addUser";
        }
        displayMessage();
        return "userList";
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
        String msg = resourceBundle.getString("users.addInfo");
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
