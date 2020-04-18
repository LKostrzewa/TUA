package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListAllRentalsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    private List<ListAllRentalsDto> rentals;

    public List<ListAllRentalsDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<ListAllRentalsDto> rentals) {
        this.rentals = rentals;
    }

    @PostConstruct
    private void init() {
        this.rentals = rentalEndpoint.getAllRentals();
    }
}
