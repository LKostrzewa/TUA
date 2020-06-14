package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.security.CurrentUser;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania wypożyczenia.
 */
@Named
@ViewScoped
public class AddRentalPageBean implements Serializable {
    private final PropertyReader propertyReader = new PropertyReader();
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
    private Locale locale;

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

    public Locale getLocale() {
        return locale;
    }
    @PostConstruct
    public void loadLocale() {
        this.locale = propertyReader.getCurrentLocale();
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() {
        this.addRentalDto = new AddRentalDto();
        this.locale = propertyReader.getCurrentLocale();
        try {
            this.addRentalDto.setYachtName(this.yachtEndpoint.getYachtById(this.yachtId).getName());
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        this.addRentalDto.setUserLogin(this.currentUser.getCurrentUserLogin());
    }

    /**
     * Metoda obsługująca kliknięcie przycisku potwierdzającego wypożyczenie danego jachtu.
     *
     * @return strona, na którą ma zostać przekierowany użytkownik
     */
    public String addRental() {
        addRentalDto.setBeginDate(setHour(addRentalDto.getBeginDate()));
        addRentalDto.setEndDate(setHour(addRentalDto.getEndDate()));

        if (addRentalDto.getBeginDate().after(addRentalDto.getEndDate())) {
            displayInit();
            String msg = resourceBundle.getString("rentals.invalidDates");
            String head = resourceBundle.getString("error");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
            return "/client/rental/addRental.xhtml?faces-redirect=true?includeViewParams=true";
        } else {
            try {
                rentalEndpoint.addRental(addRentalDto);
                displayMessage();
            } catch (AppBaseException e) {
                displayError(e.getLocalizedMessage());
                return "/client/rental/addRental.xhtml?faces-redirect=true?includeViewParams=true";
            }

            return "/client/rental/listRentals.xhtml?faces-redirect=true";
        }
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    public void displayInit() {
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

    /**
     * Metoda ustawia godzinę obiektu typu Date na 10.
     *
     * @param date obiekt, który chcemy zmodyfikować
     * @return zmodyfikowany obiekt, którego godzina to 10
     */
    private Date setHour(Date date) {
        int hour = Integer.parseInt(this.propertyReader.getProperty("config", "START_HOUR"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * Metoda zwraca obiekt typu Date reprezentujący jutrzejszy dzień.
     *
     * @return jutrzejszy dzień
     */
    public Date getTommorow() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * Metoda zwraca obiekt typu Date reprezentujący pojutrze.
     *
     * @return pojutrze
     */
    public Date getNextDayAfterTommorow() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 2);
        return calendar.getTime();
    }
}