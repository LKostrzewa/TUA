package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalStatusFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Klasa menadżera do obsługi operacji związanych z wypożyczeniami.
 */
@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalManager extends AbstractManager implements SessionSynchronization {
    private final PropertyReader propertyReader = new PropertyReader();
    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private RentalStatusFacade rentalStatusFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private YachtFacade yachtFacade;
    private String RENTAL_PENDING_STATUS;
    private String RENTAL_CANCELED_STATUS;

    @PostConstruct
    public void init() {
        RENTAL_PENDING_STATUS = propertyReader.getProperty("config", "PENDING_STATUS");
        RENTAL_CANCELED_STATUS = propertyReader.getProperty("config", "CANCELED_STATUS");
    }

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param rental obiekt encji nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addRental(Rental rental) throws AppBaseException {
        User rentingUser = userFacade.findByLogin(rental.getUser().getLogin());

        Yacht yachtToRent = yachtFacade.findByName(rental.getYacht().getName());

        if (yachtToRent.getCurrentPort() == null)
            throw YachtPortChangedException.createYachtNotAssignedException(yachtToRent);

        if (!yachtToRent.getCurrentPort().isActive())
            throw EntityNotActiveException.createPortNotActiveException(yachtToRent.getCurrentPort());

        if (!yachtToRent.isActive())
            throw EntityNotActiveException.createYachtNotActiveException(yachtToRent);

        if (rentalFacade.interfere(rental))
            throw RentalPeriodInterferenceException.createRentalPeriodInterferenceException(rental);

        RentalStatus pendingStatus = rentalStatusFacade.findByName(RENTAL_PENDING_STATUS);

        long days = (rental.getEndDate().getTime() - rental.getBeginDate().getTime()) / (1000 * 60 * 60 * 24);
        BigDecimal rentalPrice = yachtToRent.getYachtModel().getBasicPrice().multiply(yachtToRent.getPriceMultiplier()).multiply(BigDecimal.valueOf(days));
        rentalPrice = rentalPrice.setScale(2, RoundingMode.DOWN);

        Rental newRental = new Rental(rental.getBeginDate(), rental.getEndDate(), rentalPrice, rentingUser, yachtToRent);
        newRental.setRentalStatus(pendingStatus);

        rentalFacade.create(newRental);
    }

    /**
     * Metoda, która zwraca wypożyczenie o danym id.
     *
     * @param rentalBusinessKey klucz biznesowy wypożyczenia
     * @return wypożyczenie o podanym kluczu biznesowym
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"getUserRentalDetails", "cancelRental"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Rental getRentalByBusinessKey(UUID rentalBusinessKey) throws AppBaseException {
        return rentalFacade.findByBusinessKey(rentalBusinessKey).orElseThrow(AppNotFoundException::createRentalNotFoundException);
    }

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    @RolesAllowed("getRentals")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getAllRentalsByUser(String userLogin) {
        return rentalFacade.findAll()
                .stream()
                .filter(rental -> rental.getUser().getLogin().equals(userLogin))
                .collect(Collectors.toList());
    }

    /**
     * Metoda, która anuluje wypożyczenie.
     *
     * @param rentalBusinessKey klucz główny wypożyczenia, które użytkownik chce anulować
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("cancelRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cancelRental(UUID rentalBusinessKey) throws AppBaseException {
        Rental rentalToCancel = getRentalByBusinessKey(rentalBusinessKey);

        if (rentalToCancel.getRentalStatus().getName().equals(RENTAL_PENDING_STATUS)) {
            RentalStatus rentalStatus = rentalStatusFacade.findByName(RENTAL_CANCELED_STATUS);
            rentalToCancel.setRentalStatus(rentalStatus);
            rentalFacade.edit(rentalToCancel);
        } else throw RentalNotCancelableException.createRentalNotCancelableException(rentalToCancel);
    }

    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCountRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return rentalFacade.getFilteredRowCount(filters);
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
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return rentalFacade.getResultList(first, pageSize, filters);
    }

    public List<Rental> getAllRentals() {
        return rentalFacade.findAll();
    }

    public List<Rental> getFilteredRentals(String filter) {
        return rentalFacade.getFilteredRentals(filter);
    }
}
