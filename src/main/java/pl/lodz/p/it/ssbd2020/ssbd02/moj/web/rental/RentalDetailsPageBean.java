package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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
     */
    public void init() throws AppBaseException {
        this.rentalDetails = rentalEndpoint.getRentalDetails(UUID.fromString(this.rentalBusinessKey));
    }

    /**
     * Metoda anulująca wypożyczenie.
     */
    public void cancelRental() throws AppBaseException {
        rentalEndpoint.cancelRental(UUID.fromString(this.rentalBusinessKey));
    }
}
