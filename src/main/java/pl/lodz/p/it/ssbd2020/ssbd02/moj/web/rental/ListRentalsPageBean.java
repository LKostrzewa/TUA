package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.security.CurrentUser;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListRentalsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    @Inject
    private CurrentUser currentUser;
    private List<ListRentalsDto> rentals;

    public List<ListRentalsDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<ListRentalsDto> rentals) {
        this.rentals = rentals;
    }

    public void cancelRental(Long rentalId) {
        rentalEndpoint.cancelRental(rentalId);
    }

    @PostConstruct
    private void init() {
        this.rentals = rentalEndpoint.getRentals(currentUser.getCurrentUserLogin());
    }
}
