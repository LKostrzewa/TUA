package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;

public class RentalNotCancelableException extends AppBaseException {
    public static final String RENTAL_MESSAGE_KEY = "exception.rentalNotCancelable";
    private Rental rental;

    public RentalNotCancelableException(String message) {
        super(message);
    }

    public static RentalNotCancelableException createRentalNotCancelableException(Rental rental) {
        RentalNotCancelableException rentalNotCancelableException = new RentalNotCancelableException(RENTAL_MESSAGE_KEY);
        rentalNotCancelableException.setRental(rental);
        return rentalNotCancelableException;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}
