package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.*;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.RentalManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class RentalEndpointImpl implements Serializable, RentalEndpoint {
    @Inject
    private RentalManager rentalManager;

    public void addRental(AddRentalDto addRentalDto) throws AppBaseException {
        Rental rental = ObjectMapperUtils.map(addRentalDto, Rental.class);
        rentalManager.addRental(rental);
    }

    /**
     * Metoda, która zwraca wszystkie wypożyczenia
     *
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    @RolesAllowed("getAllRentals")
    public List<ListAllRentalsDto> getAllRentals() {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentals(), ListAllRentalsDto.class);
    }

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    @RolesAllowed("getRentals")
    public List<ListRentalsDto> getRentals(String userLogin) {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentalsByUser(userLogin), ListRentalsDto.class);
    }

    /**
     * Metoda, która zwraca wszystkie wypożyczenia na dany jacht.
     *
     * @param yachtName nazwa yachtu
     * @return lista wypożyczeń użytkownika o podanym loginie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getRentalsByYacht")
    public List<ListAllRentalsDto> getRentalsByYacht(String yachtName) throws AppBaseException {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentalsByYacht(yachtName), ListAllRentalsDto.class);
    }

    public EditRentalDto getRentalById(Long rentalId) throws AppBaseException {
        Rental rental = rentalManager.getRentalById(rentalId);
        return ObjectMapperUtils.map(rental, EditRentalDto.class);
    }

    public void editRental(EditRentalDto editRentalDto) throws AppBaseException {
        Rental rentalToEdit = ObjectMapperUtils.map(editRentalDto, Rental.class);
        rentalManager.editRental(rentalToEdit);
    }

    public void cancelRental(Long rentalId) throws AppBaseException {
        rentalManager.cancelRental(rentalId);
    }

    public MyRentalDetailsDto getUserRentalDetails(Long rentalId) throws AppBaseException {
        Rental rental = rentalManager.getRentalById(rentalId);
        return ObjectMapperUtils.map(rental, MyRentalDetailsDto.class);
    }
}
