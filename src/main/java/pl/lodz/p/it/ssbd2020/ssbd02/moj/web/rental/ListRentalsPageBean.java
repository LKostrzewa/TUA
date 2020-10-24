package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.security.CurrentUser;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa do obsługi widoku listy swoich wypożyczeń.
 */
@Named
@ViewScoped
public class ListRentalsPageBean implements Serializable {
    @Inject
    private @Named("RentalEndpointImpl") RentalEndpoint rentalEndpoint;
    @Inject
    private @Named("CurrentUser") CurrentUser currentUser;
    private List<ListRentalsDto> rentals;

    public List<ListRentalsDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<ListRentalsDto> rentals) {
        this.rentals = rentals;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        List<ListRentalsDto> rentals = rentalEndpoint.getRentals(currentUser.getCurrentUserLogin());
        rentals.sort(Comparator.comparing(ListRentalsDto::getBeginDate));
        rentals.sort(new Comparator<>() {
            private final List<String> definedOrder =
                    Arrays.asList("STARTED", "PENDING", "FINISHED", "CANCELLED");

            @Override
            public int compare(final ListRentalsDto r1, final ListRentalsDto r2) {
                return Integer.compare(definedOrder.indexOf(r1.getStatusName()), definedOrder.indexOf(r2.getStatusName()));
            }
        });
        this.rentals = rentals;
    }
}
