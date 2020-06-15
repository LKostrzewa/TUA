package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Klasa do obsługi widoku listy wszystkich wypożyczeń.
 */
@Named
@ViewScoped
public class ListAllRentalsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    private List<ListAllRentalsDto> rentals;
    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<ListAllRentalsDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<ListAllRentalsDto> rentals) {
        this.rentals = rentals;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        rentals = rentalEndpoint.getAllRentals();
    }

    /**
     * Metoda filtrująca wypożyczenia po nazwie yachtu
     */
    public void filterRentals(){
        rentals = rentalEndpoint.getFilteredRentals(filter);
    }
}
