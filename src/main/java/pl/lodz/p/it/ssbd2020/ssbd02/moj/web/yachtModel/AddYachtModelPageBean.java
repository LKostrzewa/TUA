package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania modelu jachtu.
 */
@Named
@RequestScoped
public class AddYachtModelPageBean {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private NewYachtModelDto newYachtModelDto;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    public NewYachtModelDto getNewYachtModelDto() {
        return newYachtModelDto;
    }

    public void setNewYachtModelDto(NewYachtModelDto newYachtModelDto) {
        this.newYachtModelDto = newYachtModelDto;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        newYachtModelDto = new NewYachtModelDto();
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do dodania nowego modelu jachtu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String addNewYachtModel() {
        try {
            yachtModelEndpoint.addYachtModel(newYachtModelDto);
            displayMessage();
        }
        catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "listYachtModels.xhtml?faces-redirect=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    private void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("yachtModel.addInfo");
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
