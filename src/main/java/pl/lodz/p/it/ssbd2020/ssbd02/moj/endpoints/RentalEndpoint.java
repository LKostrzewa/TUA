package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.*;

import java.util.List;
import java.util.Map;

public interface RentalEndpoint {

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param addRentalDto obiekt DTO z danymi nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void addRental(AddRentalDto addRentalDto) throws AppBaseException;

    /**
     * Metoda, która zwraca wszystkie wypożyczenia
     *
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    List<ListAllRentalsDto> getAllRentals();

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    List<ListRentalsDto> getRentals(String userLogin);

    /**
     * Metoda, która zwraca wszystkie wypożyczenia na dany jacht.
     *
     * @param yachtName nazwa yachtu
     * @return lista wypożyczeń jachtu o podanej nazwie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    List<ListAllRentalsDto> getRentalsByYacht(String yachtName) throws AppBaseException;

    //TODO to chyba do usunięcia
    EditRentalDto getRentalById(Long rentalId) throws AppBaseException;

    //TODO to też
    void editRental(EditRentalDto editRentalDto) throws AppBaseException;

    /**
     * Metoda, która anuluje wypożyczenie.
     *
     * @param rentalId Id wypożyczenia, które użytkownik chce anulować
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void cancelRental(Long rentalId) throws AppBaseException;

    /**
     * Metoda, która pobiera szczegóły wypożyczenia klienta.
     *
     * @param rentalId Id wypożyczenia, którego szczegóły klient chce zobaczyć
     * @return obiekt Dto ze szczegółami wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    RentalDetailsDto getUserRentalDetails(Long rentalId) throws AppBaseException;


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
}
