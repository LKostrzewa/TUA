package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku listy modeli jachtów.
 */
@Named
@ViewScoped
public class ListYachtModelPageBean implements Serializable {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private List<ListYachtModelDto> yachtModels;


    public List<ListYachtModelDto> getYachtModels() {
        return yachtModels;
    }

    public void setYachtModels(List<ListYachtModelDto> yachtModels) {
        this.yachtModels = yachtModels;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        this.yachtModels = yachtModelEndpoint.getAllYachtModels();
    }

    /**
     * Metoda do deaktywacji modelu jachtu.
     *
     * @param yachtModelId id modelu jachtu, który ma zostać deaktywowany
     * @return strona, na którą użytkownik ma zostać przekierowany
     */
    public String deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        try {
            yachtModelEndpoint.deactivateYachtModel(yachtModelId);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "listYachtModels";
    }

    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("yachtModel.deactivateInfo");
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
