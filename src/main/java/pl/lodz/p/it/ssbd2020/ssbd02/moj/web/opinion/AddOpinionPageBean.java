package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania opinii.
 */
@Named
@RequestScoped
public class AddOpinionPageBean {
    @Inject
    private OpinionEndpoint opinionEndpoint;
    @Inject
    private FacesContext facesContext;
    private NewOpinionDto newOpinionDto;
    private ResourceBundle resourceBundle;
    private String rentalBusinessKey;

    public NewOpinionDto getNewOpinionDto() {
        return newOpinionDto;
    }

    public void setNewOpinionDto(NewOpinionDto newOpinionDto) {
        this.newOpinionDto = newOpinionDto;
    }

    public String  getRentalBusinessKey() {
        return rentalBusinessKey;
    }

    public void setRentalBusinessKey(String rentalBusinessKey) {
        this.rentalBusinessKey = rentalBusinessKey;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        this.newOpinionDto = new NewOpinionDto();
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do dodania nowej opinii.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String addOpinion() {
        try {
            opinionEndpoint.addOpinion(newOpinionDto, rentalBusinessKey);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "client/rentalDetails.xhtml?faces-redirect=true?includeViewParams=true";
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
        String msg = resourceBundle.getString("opinion.addInfo");
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
