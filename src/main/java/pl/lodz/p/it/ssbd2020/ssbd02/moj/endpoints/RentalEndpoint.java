package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;

import java.util.List;
import java.util.UUID;

/**
 * Interfejs Endpoint do operacji związanych z wypożyczeniem.
 */
public interface RentalEndpoint {

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param addRentalDto obiekt DTO z danymi nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void addRental(AddRentalDto addRentalDto) throws AppBaseException;

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    List<ListRentalsDto> getRentals(String userLogin);

    /**
     * Metoda, która anuluje wypożyczenie.
     *
     * @param rentalBusinessKey klucz biznesowy wypożyczenia, które użytkownik chce anulować
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void cancelRental(UUID rentalBusinessKey) throws AppBaseException;

    /**
     * Metoda, która pobiera szczegóły wypożyczenia klienta.
     *
     * @param rentalBusinessKey klucz biznesowy wypożyczenia, którego szczegóły klient chce zobaczyć
     * @return obiekt Dto ze szczegółami wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    RentalDetailsDto getRentalDetails(UUID rentalBusinessKey) throws AppBaseException;

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia
     *
     * @return lista wszystkich wypożyczeń
     */
    List<ListAllRentalsDto> getAllRentals();

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia, w których nazwa jachtu pasuje
     * do przekazanego ciągu znaków
     *
     * @return lista wszystkich wypożyczeń, których nazwa jachtu pasuje do wzorca
     */
    List<ListAllRentalsDto> getFilteredRentals(String filter);
}
