package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Klasa do obsługi widoku szczegółowych informacji wypożyczenia.
 */
@Named
@ViewScoped
public class RentalDetailsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    private RentalDetailsDto rentalDetails;
    private Long rentalId;

    public RentalDetailsDto getRentalDetails() {
        return rentalDetails;
    }

    public void setRentalDetails(RentalDetailsDto rentalDetails) {
        this.rentalDetails = rentalDetails;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws AppBaseException {
        this.rentalDetails = rentalEndpoint.getUserRentalDetails(rentalId);
    }

    /**
     * Metoda anulująca wypożyczenie.
     */
    public void cancelRental() throws AppBaseException {
        rentalEndpoint.cancelRental(rentalId);
    }
}
