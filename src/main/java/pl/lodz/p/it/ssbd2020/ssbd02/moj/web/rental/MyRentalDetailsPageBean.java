package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.MyRentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MyRentalDetailsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    private MyRentalDetailsDto rentalDetails;
    private Long rentalId;

    public MyRentalDetailsDto getRentalDetails() {
        return rentalDetails;
    }

    public void setRentalDetails(MyRentalDetailsDto rentalDetails) {
        this.rentalDetails = rentalDetails;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public void init() throws AppBaseException {
        this.rentalDetails = rentalEndpoint.getUserRentalDetails(rentalId);
    }

    public void cancelRental() throws AppBaseException {
        rentalEndpoint.cancelRental(rentalId);
    }
}
