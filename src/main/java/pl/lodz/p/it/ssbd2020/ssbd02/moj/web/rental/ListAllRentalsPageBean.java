package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ListAllRentalsPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    private LazyDataModel<ListAllRentalsDto> rentals;

    public LazyDataModel<ListAllRentalsDto> getRentals() {
        return rentals;
    }

    public void setRentals(LazyDataModel<ListAllRentalsDto> rentals) {
        this.rentals = rentals;
    }

    @PostConstruct
    private void init() {
        rentals = new LazyDataModel<>() {
            @Override
            public List<ListAllRentalsDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
                rentals.setRowCount(rentalEndpoint.getFilteredRowCount(filters));
                return rentalEndpoint.getResultList(first, pageSize, filters);
            }
        };
    }
}
