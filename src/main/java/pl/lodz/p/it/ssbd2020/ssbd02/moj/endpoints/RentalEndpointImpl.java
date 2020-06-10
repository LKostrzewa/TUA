package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.RentalManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Implementacja interfejsu RentalEndpoint.
 */
@Stateful
@Interceptors(LoggerInterceptor.class)
public class RentalEndpointImpl implements Serializable, RentalEndpoint {
    @Inject
    private RentalManager rentalManager;

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param addRentalDto obiekt DTO z danymi nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addRental")
    public void addRental(AddRentalDto addRentalDto) throws AppBaseException {
        Rental rental = ObjectMapperUtils.map(addRentalDto, Rental.class);
        rentalManager.addRental(rental);
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
     * Metoda, która anuluje wypożyczenie.
     *
     * @param rentalId Id wypożyczenia, które użytkownik chce anulować
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("cancelRental")
    public void cancelRental(Long rentalId) throws AppBaseException {
        rentalManager.cancelRental(rentalId);
    }

    /**
     * Metoda, która pobiera szczegóły wypożyczenia klienta.
     *
     * @param rentalId Id wypożyczenia, którego szczegóły klient chce zobaczyć
     * @return obiekt Dto ze szczegółami wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getUserRentalDetails")
    public RentalDetailsDto getUserRentalDetails(Long rentalId) throws AppBaseException {
        Rental rental = rentalManager.getRentalById(rentalId);
        return ObjectMapperUtils.map(rental, RentalDetailsDto.class);
    }

    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCountRental")
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return rentalManager.getFilteredRowCount(filters);
    }

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed("getResultListRental")
    public List<ListAllRentalsDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return ObjectMapperUtils.mapAll(rentalManager.getResultList(first, pageSize, filters), ListAllRentalsDto.class);
    }

    @Override
    public List<ListAllRentalsDto> getAllRentals() {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentals(), ListAllRentalsDto.class);
    }

    @Override
    public List<ListAllRentalsDto> getFilteredRentals(String filter) {
        return ObjectMapperUtils.mapAll(rentalManager.getFilteredRentals(filter), ListAllRentalsDto.class);
    }
}
