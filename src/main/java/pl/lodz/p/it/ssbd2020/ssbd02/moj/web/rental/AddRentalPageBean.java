package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.security.CurrentUser;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania wypożyczenia.
 */
@Named
@ViewScoped
public class AddRentalPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    @Inject
    private CurrentUser currentUser;
    private AddRentalDto addRentalDto;
    private long yachtId;

    public AddRentalDto getAddRentalDto() {
        return addRentalDto;
    }

    public void setAddRentalDto(AddRentalDto addRentalDto) {
        this.addRentalDto = addRentalDto;
    }

    public long getYachtId() {
        return yachtId;
    }

    public void setYachtId(long yachtId) {
        this.yachtId = yachtId;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() {
        addRentalDto = new AddRentalDto();
        try {
            addRentalDto.setYachtName(yachtEndpoint.getYachtById(yachtId).getName());
            addRentalDto.setUserLogin(currentUser.getCurrentUserLogin());
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda obsługująca kliknięcie przycisku potwierdzającego wypożyczenie danego jachtu.
     *
     * @return strona, na którą ma zostać przekierowany użytkownik
     */
    public String addRental() {
        try {
            rentalEndpoint.addRental(addRentalDto);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }

        return "/client/rental/listRentals.xhtml?faces-redirect=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie
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