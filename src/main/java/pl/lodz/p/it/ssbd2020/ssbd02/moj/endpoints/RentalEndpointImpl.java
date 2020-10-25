package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.RentalManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja interfejsu RentalEndpoint.
 */
@Stateful(name = "RentalEndpointImpl")
@Named("RentalEndpointImpl")
@Interceptors(LoggerInterceptor.class)
public class RentalEndpointImpl implements Serializable, RentalEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private RentalManager rentalManager;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param addRentalDto obiekt DTO z danymi nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void addRental(AddRentalDto addRentalDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                Rental rental = new Rental(addRentalDto.getBeginDate(), addRentalDto.getEndDate()
                        , null, new User(addRentalDto.getUserLogin()), new Yacht(addRentalDto.getYachtName()));
                rentalManager.addRental(rental);
                rollback = rentalManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public List<ListRentalsDto> getRentals(String userLogin) {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentalsByUser(userLogin), ListRentalsDto.class);
    }

    /**
     * Metoda, która anuluje wypożyczenie.
     *
     * @param rentalBusinessKey klucz biznesowy wypożyczenia, które użytkownik chce anulować
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void cancelRental(UUID rentalBusinessKey) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                rentalManager.cancelRental(rentalBusinessKey);
                rollback = rentalManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która pobiera szczegóły wypożyczenia klienta.
     *
     * @param rentalBusinessKey klucz biznesowy wypożyczenia, którego szczegóły klient chce zobaczyć
     * @return obiekt Dto ze szczegółami wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public RentalDetailsDto getRentalDetails(UUID rentalBusinessKey) throws AppBaseException {
        Rental rental = rentalManager.getRentalByBusinessKey(rentalBusinessKey);
        return ObjectMapperUtils.map(rental, RentalDetailsDto.class);
    }

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia
     *
     * @return lista wszystkich wypożyczeń
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public List<ListAllRentalsDto> getAllRentals() {
        return ObjectMapperUtils.mapAll(rentalManager.getAllRentals(), ListAllRentalsDto.class);
    }

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia, w których nazwa jachtu pasuje
     * do przekazanego ciągu znaków
     *
     * @return lista wszystkich wypożyczeń, których nazwa jachtu pasuje do wzorca
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public List<ListAllRentalsDto> getFilteredRentals(String filter) {
        return ObjectMapperUtils.mapAll(rentalManager.getFilteredRentals(filter), ListAllRentalsDto.class);
    }
}
