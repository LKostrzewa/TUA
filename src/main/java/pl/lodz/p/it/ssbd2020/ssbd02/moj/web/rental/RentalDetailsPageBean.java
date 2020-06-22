package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Klasa do obsługi widoku szczegółowych informacji wypożyczenia.
 */
@Named
@ViewScoped
public class RentalDetailsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    private RentalDetailsDto rentalDetails;
    private String rentalBusinessKey;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    public RentalDetailsDto getRentalDetails() {
        return rentalDetails;
    }

    public void setRentalDetails(RentalDetailsDto rentalDetails) {
        this.rentalDetails = rentalDetails;
    }

    public String getRentalBusinessKey() {
        return rentalBusinessKey;
    }

    public void setRentalBusinessKey(String rentalBusinessKey) {
        this.rentalBusinessKey = rentalBusinessKey;
    }

    /**
     * Metoda inicjalizująca komponent.
     *
     */
    public void init() {
        try {
            this.rentalDetails = rentalEndpoint.getRentalDetails(UUID.fromString(this.rentalBusinessKey));
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda anulująca wypożyczenie.
     */
    public void cancelRental(){
        try{
            rentalEndpoint.cancelRental(UUID.fromString(this.rentalBusinessKey));
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
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
    private void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("rentals.addInfo");
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
