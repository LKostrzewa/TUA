package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.EditRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.RentalManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalEndpoint implements Serializable {
    @Inject
    private RentalManager rentalManager;

    public void addRental(AddRentalDto addRentalDto) {
        Rental rental = ObjectMapperUtils.map(addRentalDto, Rental.class);
        rentalManager.addRental(rental);
    }

    public List<ListAllRentalsDto> getAllRentals() {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentals(), ListAllRentalsDto.class);
    }

    public List<ListRentalsDto> getRentals(String userLogin) {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentalsByUser(userLogin), ListRentalsDto.class);
    }

    public List<ListAllRentalsDto> getRentalsByYacht(String yachtName){
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentalsByYacht(yachtName), ListAllRentalsDto.class);
    }

    public EditRentalDto getRentalById(Long rentalId) {
        Rental rental = rentalManager.getRentalById(rentalId);
        return ObjectMapperUtils.map(rental, EditRentalDto.class);
    }

    public void editRental(EditRentalDto editRentalDto) {
        Rental rentalToEdit = ObjectMapperUtils.map(editRentalDto, Rental.class);
        rentalManager.editRental(rentalToEdit);
    }

    public void cancelRental(Long rentalId) {
        rentalManager.cancelRental(rentalId);
    }
}
