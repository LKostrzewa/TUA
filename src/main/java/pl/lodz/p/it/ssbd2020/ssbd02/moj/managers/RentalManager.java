package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.*;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Klasa menadżera do obsługi operacji związanych z wypożyczeniami.
 */
@Stateful(name = "RentalManager")
@Named("RentalManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalManager extends AbstractManager implements SessionSynchronization {
    private final PropertyReader propertyReader = new PropertyReader();
    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private RentalStatusFacade rentalStatusFacade;
    @Inject
    private UserFacadeMoj userFacadeMoj;
    @Inject
    private YachtFacade yachtFacade;
    @Inject
    private PortFacade portFacade;

    private String RENTAL_PENDING_STATUS;
    private String RENTAL_CANCELLED_STATUS;

    @PostConstruct
    public void init() {
        RENTAL_PENDING_STATUS = propertyReader.getProperty("config", "PENDING_STATUS");
        RENTAL_CANCELLED_STATUS = propertyReader.getProperty("config", "CANCELLED_STATUS");
    }

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param rental obiekt encji nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addRental(Rental rental) throws AppBaseException {
        try {
            User rentingUser = userFacadeMoj.findByLogin(rental.getUser().getLogin());

            Yacht yachtToRent = yachtFacade.findByName(rental.getYacht().getName());

            yachtFacade.lock(yachtToRent, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            portFacade.lock(yachtToRent.getCurrentPort(), LockModeType.PESSIMISTIC_FORCE_INCREMENT);

            if (yachtToRent.getCurrentPort() == null)
                throw YachtPortChangedException.createYachtNotAssignedException(yachtToRent);

            if (!yachtToRent.getCurrentPort().isActive())
                throw EntityNotActiveException.createPortNotActiveException(yachtToRent.getCurrentPort());

            if (!yachtToRent.isActive())
                throw EntityNotActiveException.createYachtNotActiveException(yachtToRent);

            if (rentalFacade.interfere(rental))
                throw RentalPeriodInterferenceException.createRentalPeriodInterferenceException(rental);

            RentalStatus pendingStatus = rentalStatusFacade.findByName(RENTAL_PENDING_STATUS);

        int thousand = Integer.parseInt(propertyReader.getProperty("config", "THOUSAND"));
        int minutes = Integer.parseInt(propertyReader.getProperty("config", "MINUTES"));
        int hours = Integer.parseInt(propertyReader.getProperty("config", "HOURS"));


        long days = (rental.getEndDate().getTime() - rental.getBeginDate().getTime()) / (thousand * minutes * minutes * hours);
        BigDecimal rentalPrice = yachtToRent.getYachtModel().getBasicPrice().multiply(yachtToRent.getPriceMultiplier()).multiply(BigDecimal.valueOf(days));
        rentalPrice = rentalPrice.setScale(2, RoundingMode.DOWN);

            Rental newRental = new Rental(rental.getBeginDate(), rental.getEndDate(), rentalPrice, rentingUser, yachtToRent);
            newRental.setRentalStatus(pendingStatus);

            rentalFacade.create(newRental);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która zwraca wypożyczenie o danym id.
     *
     * @param rentalBusinessKey klucz biznesowy wypożyczenia
     * @return wypożyczenie o podanym kluczu biznesowym
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"CLIENT"})
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
    @RolesAllowed({"CLIENT"})
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
    @RolesAllowed({"CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cancelRental(UUID rentalBusinessKey) throws AppBaseException {
        try {
            Rental rentalToCancel = getRentalByBusinessKey(rentalBusinessKey);

            if (rentalToCancel.getRentalStatus().getName().equals(RENTAL_PENDING_STATUS)) {
                RentalStatus rentalStatus = rentalStatusFacade.findByName(RENTAL_CANCELLED_STATUS);
                rentalToCancel.setRentalStatus(rentalStatus);
                rentalFacade.edit(rentalToCancel);
            } else throw RentalNotCancelableException.createRentalNotCancelableException(rentalToCancel);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia
     *
     * @return lista wszystkich wypożyczeń
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getAllRentals() {
        return rentalFacade.findAll();
    }

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia, w których nazwa jachtu pasuje
     * do przekazanego ciągu znaków
     *
     * @param filter ciąg znaków według którego będą filtrowane wypożyczenia
     * @return lista wszystkich wypożyczeń, których nazwa jachtu pasuje do wzorca
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getFilteredRentals(String filter) {
        return rentalFacade.getFilteredRentals(filter);
    }
}
