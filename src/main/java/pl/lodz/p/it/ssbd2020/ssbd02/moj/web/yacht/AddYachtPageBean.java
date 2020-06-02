package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania jachtu.
 */
@Named
@RequestScoped
public class AddYachtPageBean {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private FacesContext facesContext;

    private NewYachtDto newYachtDto;
    private ResourceBundle resourceBundle;

    public NewYachtDto getNewYachtDto() {
        return newYachtDto;
    }

    public void setNewYachtDto(NewYachtDto newYachtDto) {
        this.newYachtDto = newYachtDto;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        newYachtDto = new NewYachtDto();
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do dodania nowego jachtu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String addNewYacht() throws AppBaseException {
        try {
            yachtEndpoint.addYacht(newYachtDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "listYachts.xhtml?faces-redirect=true?includeViewParams=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("users.addInfo");
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
