package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;

public class RentalNotFinishedException extends AppBaseException{
    public static final String RENTAL_MESSAGE_KEY = "exception.rentalNotFinished";
    private Rental rental;

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public RentalNotFinishedException(String message) {
        super(message);
    }

    public static RentalNotFinishedException createRentalNotFinishedException(Rental rental){
        RentalNotFinishedException rnfe = new RentalNotFinishedException(RENTAL_MESSAGE_KEY);
        rnfe.setRental(rental);
        return rnfe;
    }
}
