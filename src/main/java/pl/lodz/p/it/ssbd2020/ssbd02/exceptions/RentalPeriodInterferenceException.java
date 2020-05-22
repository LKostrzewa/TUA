package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;

public class RentalPeriodInterferenceException extends AppBaseException {
    public static final String RENTAL_MESSAGE_KEY = "exception.rentalPeriodInterference";
    private Rental rental;

    public RentalPeriodInterferenceException(String message) {
        super(message);
    }

    public static RentalPeriodInterferenceException createRentalPeriodInterferenceException(Rental rental) {
        RentalPeriodInterferenceException rentalPeriodInterferenceException = new RentalPeriodInterferenceException(RENTAL_MESSAGE_KEY);
        rentalPeriodInterferenceException.setRental(rental);
        return rentalPeriodInterferenceException;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}
