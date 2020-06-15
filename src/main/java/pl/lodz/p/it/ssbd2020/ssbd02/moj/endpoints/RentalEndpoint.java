package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.*;

import java.util.List;
import java.util.Map;
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
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    int getFilteredRowCount(Map<String, FilterMeta> filters);

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    List<ListAllRentalsDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters);

    List<ListAllRentalsDto> getAllRentals();

    List<ListAllRentalsDto> getFilteredRentals(String filter);
}
