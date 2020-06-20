package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtListDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku listy jachtów.
 */
@Named
@ViewScoped
public class ListYachtPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private List<YachtListDto> yachts;

    public List<YachtListDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtListDto> yachts) {
        this.yachts = yachts;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        this.yachts = yachtEndpoint.getAllYachts();
        yachts.sort(Comparator.comparing(YachtListDto::getName,String::compareToIgnoreCase));
    }

    /**
     * Metoda do deaktywacji jachtu.
     *
     * @param yachtID id jachtu, który ma zostać deaktywowany
     * @return strona, na którą użytkownik ma zostać przekierowany
     */
    public String deactivateYacht(Long yachtID) {
        try {
            yachtEndpoint.deactivateYacht(yachtID);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "listYachts";
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
        String msg = resourceBundle.getString("yacht.deactivateInfo");
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
