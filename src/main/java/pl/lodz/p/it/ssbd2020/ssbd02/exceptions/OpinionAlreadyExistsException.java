package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;

public class OpinionAlreadyExistsException extends AppBaseException {
    public static final String OPINION_ALREADY_EXISTS_KEY = "exception.opinionAlreadyExists";

    private Rental rental;

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public OpinionAlreadyExistsException(String message) {
        super(message);
    }

    public static OpinionAlreadyExistsException createOpinionAlreadyExistsException(Rental rental){
        OpinionAlreadyExistsException opinionAlreadyExistsException = new OpinionAlreadyExistsException(OPINION_ALREADY_EXISTS_KEY);
        opinionAlreadyExistsException.setRental(rental);
        return opinionAlreadyExistsException;
    }
}
